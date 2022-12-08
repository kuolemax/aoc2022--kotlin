fun main() {

    fun part1(input: List<String>): Int {
        return findLessThanSizeDirectoryTotalSize(input)
    }

    fun part2(input: List<String>): Int {
        val needSpace = 30_000_000
        val totalSize = 70_000_000
        return findTheSmallestDirectoryThatShouldBeDeleted(parseToDirectories(input), needSpace, totalSize)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24_933_642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private fun findTheSmallestDirectoryThatShouldBeDeleted(directories: List<Directory>, needSpace: Int, totalSize: Int): Int {
    val currentLeftSpace = totalSize - directories.maxBy { it.totalSize!! }.totalSize!!
    val needLeftSpace = needSpace - currentLeftSpace
    return directories
        .filter { it.totalSize!! >= needLeftSpace }
        .minBy { it.totalSize!! }
        .totalSize!!
}

private fun findLessThanSizeDirectoryTotalSize(rows: List<String>): Int {
    val files = parseToDirectories(rows)
    return files.filter { it.totalSize!! < 100_000 }.sumOf { it.totalSize!! }
}

private fun parseToDirectories(rows: List<String>): MutableList<Directory> {
    val files = mutableListOf<Directory>()
    var currentDir: Directory? = null
    rows.forEach { row ->
        val trimRow = row.trim()
        if (trimRow[0].isDigit()) {
            // 文件
            val (size, _) = trimRow.split(" ")
            currentDir!!.size += size.toInt()
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
    return files
}

data class Directory(
    var name: String,
    var parent: Directory? = null,
    var size: Int = 0,
    var totalSize: Int? = null,
    var children: MutableList<Directory> = mutableListOf()
)

private fun computeTotalSize(file: Directory, files: List<Directory>): Int {
    // 如果 totalSize 不为 null, 是设置过值的
    if (file.totalSize != null) {
        return file.totalSize!!.toInt()
    }

    // 找出所有子集的 totalSize
    val children = files.filter { it.parent == file }

    if (children.isEmpty()) {
        // 叶子
        return file.size
    }

    // 非叶子
    var totalSize: Int = file.size
    for (child in children) {
        if (child.totalSize == null) {
            totalSize += computeTotalSize(child, files)
            continue
        }
        totalSize += child.totalSize!!.toInt()
    }
    return totalSize
}