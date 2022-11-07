package priorityqueue

import chisel3._
import chisel3.util._


// TODO : support enq & deq at the same cycle
class PriorityQueueStageIO(keyWidth: Int, value: ValueInfo) extends Bundle {
  val output_prev = KeyValue(keyWidth, value)
  val output_nxt = KeyValue(keyWidth, value)
  val input_prev = Flipped(KeyValue(keyWidth, value))
  val input_nxt = Flipped(KeyValue(keyWidth, value))

  val cmd = Flipped(Valid(UInt(1.W)))
  val insert_here = Input(Bool())
  val cur_input_keyval = Flipped(KeyValue(keyWidth, value))
  val cur_output_keyval = KeyValue(keyWidth, value)
}

class PriorityQueueStage(keyWidth: Int, value: ValueInfo) extends Module {
  val io = IO(new PriorityQueueStageIO(keyWidth, value))

  val CMD_DEQ = 0.U
  val CMD_ENQ = 1.U

  val MAX_VALUE = (1 << keyWidth) - 1
  val key_reg = RegInit(MAX_VALUE.U(keyWidth.W))
  val value_reg = Reg(value)

  io.output_prev.key := key_reg
  io.output_prev.value := value_reg

  io.output_nxt.key := key_reg
  io.output_nxt.value := value_reg

  io.cur_output_keyval.key := key_reg
  io.cur_output_keyval.value := value_reg

  when (io.cmd.valid) {
    switch (io.cmd.bits) {
      is (CMD_DEQ) {
        key_reg := io.input_nxt.key
        value_reg := io.input_nxt.value
      }
      is (CMD_ENQ) {
        when (io.insert_here) {
          key_reg := io.cur_input_keyval.key
          value_reg := io.cur_input_keyval.value
        } .elsewhen (key_reg >= io.cur_input_keyval.key) {
          key_reg := io.input_prev.key
          value_reg := io.input_prev.value
        } .otherwise {
          // do nothing
        }
      }
    }
  }
}

object PriorityQueueStage {
  def apply(keyWidth: Int, v: ValueInfo): PriorityQueueStage = new PriorityQueueStage(keyWidth, v)
}

// TODO
// - This design is not scalable as the enqued_keyval is broadcasted to all the stages
// - Add pipeline registers later
class PriorityQueueIO(keyWidth: Int, value: ValueInfo) extends Bundle {
  val cmd = Flipped(Valid(UInt(1.W)))
  val enqued_keyval = Flipped(KeyValue(keyWidth, value))
  val dequed_keyval = Valid(KeyValue(keyWidth, value))
}

class PriorityQueue(queSize: Int, keyWidth: Int, value: ValueInfo) extends Module {
  val keyWidthInternal = keyWidth + 1
  val CMD_DEQ = 0.U
  val CMD_ENQ = 1.U

  val io = IO(new PriorityQueueIO(keyWidthInternal, value))

  val MAX_VALUE = ((1 << keyWidthInternal) - 1).U

  val stages = Seq.fill(queSize)(Module(new PriorityQueueStage(keyWidthInternal, value)))

  for (i <- 0 until (queSize - 1)) {
    stages(i+1).io.input_prev <> stages(i).io.output_nxt
    stages(i).io.input_nxt <> stages(i+1).io.output_prev
  }
  stages(queSize-1).io.input_nxt.key := MAX_VALUE
// stages(queSize-1).io.input_nxt.value :=
  stages(queSize-1).io.input_nxt.value.symbol := 0.U
  stages(queSize-1).io.input_nxt.value.child(0) := 0.U
  stages(queSize-1).io.input_nxt.value.child(1) := 0.U
  stages(0).io.input_prev.key := io.enqued_keyval.key
  stages(0).io.input_prev.value <> io.enqued_keyval.value

  for (i <- 0 until queSize) {
    stages(i).io.cmd <> io.cmd
    stages(i).io.cur_input_keyval <> io.enqued_keyval
  }


  val is_large_or_equal = WireInit(VecInit(Seq.fill(queSize)(false.B)))
  for (i <- 0 until queSize) {
    is_large_or_equal(i) := (stages(i).io.cur_output_keyval.key >= io.enqued_keyval.key)
  }

  val is_large_or_equal_cat = Wire(UInt(queSize.W))
  is_large_or_equal_cat := Cat(is_large_or_equal.reverse)

  val insert_here_idx = PriorityEncoder(is_large_or_equal_cat)

  for (i <- 0 until queSize) {
    when (i.U === insert_here_idx) {
      stages(i).io.insert_here := true.B
    } .otherwise {
      stages(i).io.insert_here := false.B
    }
  }


  io.dequed_keyval.valid := io.cmd.valid && (io.cmd.bits === CMD_DEQ) && (stages(0).io.output_prev.key =/= MAX_VALUE)
  io.dequed_keyval.bits <> stages(0).io.output_prev
}
