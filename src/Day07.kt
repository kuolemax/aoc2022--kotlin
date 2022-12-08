fun main() {

    fun part1(input: List<String>): Long {
        return findLessThanSizeDirectoryTotalSize(input)
    }

    // fun part2(input: List<String>): Int {
    //     TODO()
    // }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day07_test")
    // check(part1(testInput) == 95437.toLong())
    // check(part2(testInput) == 19)

    val input = readInput("Day07")
    println(part1(input))
    // println(part2(input))
}

private fun findLessThanSizeDirectoryTotalSize(rows: List<String>): Long {
    val files = mutableListOf<Directory>()
    var currentDir: Directory? = null
    rows.forEach { row ->
        val trimRow = row.trim()
        if (trimRow[0].isDigit()) {
            // 文件
            val (size, _) = trimRow.split(" ")
            currentDir!!.size += size.toLong()
        } else if (trimRow.startsWith("$ cd")) {
            // 切换目录
            val changeDirName = trimRow.replace("$ cd ", "")

            // 返回上级目录
            if (".." == changeDirName) {
                currentDir = currentDir?.parent
            } else {
                val file = Directory(
                    name = changeDirName,
                    parent = currentDir
                )
                currentDir = file
                files.add(file)
            }
        }
    }

    for (file in files) {
        file.totalSize = computeTotalSize(file, files)
    }

    return files.filter { it.totalSize!! < 100_000 }.sumOf { it.totalSize!! }
}

data class Directory(
    var name: String,
    var parent: Directory? = null,
    var size: Long = 0,
    var totalSize: Long? = null,
    var children: MutableList<Directory> = mutableListOf()
)

private fun computeTotalSize(file:Directory, files: List<Directory>): Long {
    // 如果 totalSize 不为 null, 是设置过值的
    if (file.totalSize != null) {
        return file.totalSize!!.toLong()
    }

    // 找出所有子集的 totalSize
    val children = files.filter { it.parent == file }

    if (children.isEmpty()) {
        // 叶子
        return file.size
    }

    // 非叶子
    var totalSize: Long = file.size
    for (child in children) {
        if (child.totalSize == null) {
            totalSize += computeTotalSize(child, files)
            continue
        }
        totalSize += child.totalSize!!.toLong()
    }
    return totalSize
}