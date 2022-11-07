package priorityqueue

import chisel3._
import chisel3.util._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


class testBundle extends Bundle {
  val v1 = UInt(8.W)
  val v2 = UInt(8.W)
}

// class PriorityQueueTester extends ChiselFlatSpec {
// class PriorityQueueStageTester extends AnyFlatSpec with ChiselScalatestTester {
// behavior of "PriorityQueueStage"
// it should "Work very very well" in {
// test(new PriorityQueueStage(4, new testBundle)) { c =>
// c.io.input_prev.key.poke(1.U)
// c.io.input_prev.value.v1.poke(11.U)
// c.io.input_prev.value.v2.poke(21.U)

// c.io.input_nxt.key.poke(2.U)
// c.io.input_nxt.value.v1.poke(12.U)
// c.io.input_nxt.value.v2.poke(22.U)

// c.io.cur_input_keyval.key.poke(3.U)
// c.io.cur_input_keyval.value.v1.poke(13.U)
// c.io.cur_input_keyval.value.v2.poke(23.U)

// c.io.insert_here.poke(false.B)

// c.clock.step()

// check if insert_here works
// c.io.cmd.valid.poke(true.B)
// c.io.cmd.bits.poke(1.U)
// c.io.insert_here.poke(true.B)
// c.clock.step()
// c.io.cur_output_keyval.key.expect(3.U)
// c.io.cur_output_keyval.value.v1.expect(13.U)
// c.io.cur_output_keyval.value.v2.expect(23.U)

// check if shift left works
// c.io.cmd.valid.poke(true.B)
// c.io.cmd.bits.poke(1.U)
// c.io.insert_here.poke(false.B)
// c.clock.step()
// c.io.cur_output_keyval.key.expect(1.U)
// c.io.cur_output_keyval.value.v1.expect(11.U)
// c.io.cur_output_keyval.value.v2.expect(21.U)


// check if shift right works
// c.io.cmd.valid.poke(true.B)
// c.io.cmd.bits.poke(0.U)
// c.io.insert_here.poke(true.B)
// c.clock.step()
// c.io.cur_output_keyval.key.expect(2.U)
// c.io.cur_output_keyval.value.v1.expect(12.U)
// c.io.cur_output_keyval.value.v2.expect(22.U)
// }
// }

// }

class PriorityQueueTester extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "PriorityQueue"
  it should "Also work very very well" in {
    test(new PriorityQueue(4, 4, UInt(4.W))) { c =>
      c.io.cmd.valid.poke(false.B)
      c.clock.step()

      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(0.U)
      c.io.dequed_keyval.valid.expect(false.B)
      c.io.dequed_keyval.bits.key.expect(31.U)

    }
  }
}
