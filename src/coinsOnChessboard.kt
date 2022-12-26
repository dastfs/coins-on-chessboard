fun main() {
    val board = mutableMapOf<Int, CoinSide>()

    //fill the grid
    for (i in 0..63) {
        board[i] = CoinSide.pickOneRandomly()
    }

    val target = (0..63).random()
    val boardParity = getBoardParity(board)
    val coinPositionToFlip = target.xor(Integer.parseInt(boardParity, 2))

    println("target       " + target.toBinary(6))
    println("                xor")
    println("board        $boardParity")
    println("-------------------")
    println("coin to flip " + coinPositionToFlip.toBinary(6) + " = $coinPositionToFlip")
}

private fun getBoardParity(board: MutableMap<Int, CoinSide>): String {
    val bit1 = board.count { it.key % 2 == 0 && it.value == CoinSide.HEAD }.toParity()
    val bit2 =
        board.count { it.value == CoinSide.HEAD && (it.key in 2..58 step 8 || it.key in 3..59 step 8 || it.key in 6..62 step 8 || it.key in 7..63 step 8) }
            .toParity()
    val bit3 =
        board.count { it.value == CoinSide.HEAD && (it.key in 4..60 step 8 || it.key in 5..61 step 8 || it.key in 6..62 step 8 || it.key in 7..63 step 8) }
            .toParity()
    val bit4 =
        board.count { it.value == CoinSide.HEAD && (it.key in 8..15 || it.key in 24..31 || it.key in 40..47 || it.key in 58..63) }
            .toParity()
    val bit5 = board.count { it.value == CoinSide.HEAD && (it.key in 16..31 || it.key in 48..63) }.toParity()
    val bit6 = board.count { it.value == CoinSide.HEAD && it.key > 31 }.toParity()
    return "$bit6$bit5$bit4$bit3$bit2$bit1"
}

private fun Int.toParity(): Byte {
    return if (this % 2 == 0) 0 else 1
}

fun Int.toBinary(len: Int): String {
    return String.format("%" + len  + "s", this.toString(2)).replace(" ".toRegex(), "0")
}

enum class CoinSide {
    HEAD, TAIL;

    companion object {
        fun pickOneRandomly(): CoinSide {
            return values().random()
        }
    }
}


