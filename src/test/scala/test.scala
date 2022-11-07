package priorityqueue

import chisel3._
import chisel3.util._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class PriorityQueueTester extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "PriorityQueue"
  it should "Also work very very well" in {
    test(new PriorityQueue(4, 4, new ValueInfo)) { c =>
      c.io.cmd.valid.poke(false.B)
      c.clock.step()

      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(0.U)
      c.io.dequed_keyval.valid.expect(false.B)
      c.io.dequed_keyval.bits.key.expect(31.U)

      c.clock.step()
      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(1.U)
      c.io.enqued_keyval.key.poke(3.U)
      c.io.enqued_keyval.value.symbol.poke(3.U)
      c.io.enqued_keyval.value.child(0).poke(3.U)
      c.io.enqued_keyval.value.child(1).poke(3.U)

      c.clock.step()
      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(1.U)
      c.io.enqued_keyval.key.poke(4.U)
      c.io.enqued_keyval.value.symbol.poke(4.U)
      c.io.enqued_keyval.value.child(0).poke(4.U)
      c.io.enqued_keyval.value.child(1).poke(4.U)

      c.clock.step()
      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(1.U)
      c.io.enqued_keyval.key.poke(2.U)
      c.io.enqued_keyval.value.symbol.poke(2.U)
      c.io.enqued_keyval.value.child(0).poke(2.U)
      c.io.enqued_keyval.value.child(1).poke(2.U)

      c.clock.step()
      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(0.U)
      c.io.dequed_keyval.valid.expect(true.B)
      c.io.dequed_keyval.bits.key.expect(2.U)

      c.clock.step()
      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(0.U)
      c.io.dequed_keyval.valid.expect(true.B)
      c.io.dequed_keyval.bits.key.expect(3.U)

      c.clock.step()
      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(1.U)
      c.io.enqued_keyval.key.poke(5.U)
      c.io.enqued_keyval.value.symbol.poke(5.U)
      c.io.enqued_keyval.value.child(0).poke(5.U)
      c.io.enqued_keyval.value.child(1).poke(5.U)

      c.clock.step()
      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(1.U)
      c.io.enqued_keyval.key.poke(2.U)
      c.io.enqued_keyval.value.symbol.poke(2.U)
      c.io.enqued_keyval.value.child(0).poke(2.U)
      c.io.enqued_keyval.value.child(1).poke(2.U)

      c.clock.step()
      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(1.U)
      c.io.enqued_keyval.key.poke(2.U)
      c.io.enqued_keyval.value.symbol.poke(2.U)
      c.io.enqued_keyval.value.child(0).poke(2.U)
      c.io.enqued_keyval.value.child(1).poke(2.U)

      c.clock.step()
      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(0.U)
      c.io.dequed_keyval.valid.expect(true.B)
      c.io.dequed_keyval.bits.key.expect(2.U)

      c.clock.step()
      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(0.U)
      c.io.dequed_keyval.valid.expect(true.B)
      c.io.dequed_keyval.bits.key.expect(2.U)

      c.clock.step()
      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(0.U)
      c.io.dequed_keyval.valid.expect(true.B)
      c.io.dequed_keyval.bits.key.expect(4.U)

      c.clock.step()
      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(0.U)
      c.io.dequed_keyval.valid.expect(true.B)
      c.io.dequed_keyval.bits.key.expect(5.U)

      c.clock.step()
      c.io.cmd.valid.poke(true.B)
      c.io.cmd.bits.poke(0.U)
      c.io.dequed_keyval.valid.expect(false.B)
// c.io.dequed_keyval.bits.key.expect(5.U)



    }
  }
}
