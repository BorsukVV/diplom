package ru.netology.nmedia.db

object PostTable {
    enum class Column(val columnName: String) {
        Id("id"),
        AuthorName("author"),
        Date("date"),
        Text("text"),
        IsLiked("isLiked"),
        LikesCount("likesCount"),
        IsReposted("isReposted"),
        RepostsCount("repostsCount"),
        ViewsCount("viewsCount"),
        VideoUrl("videoURL"),
    }

    const val NAME = "posts"

    val DDL = """
        CREATE TABLE $NAME(
        ${Column.Id.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${Column.AuthorName.columnName} TEXT NOT NULL,
        ${Column.Date.columnName} TEXT NOT NULL,
        ${Column.Text.columnName} TEXT NOT NULL,
        ${Column.IsLiked.columnName} BOOLEAN NOT NULL DEFAULT FALSE,
        ${Column.LikesCount.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.IsReposted.columnName} BOOLEAN NOT NULL DEFAULT FALSE,
        ${Column.RepostsCount.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.ViewsCount.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.VideoUrl.columnName} TEXT)
    """.trimIndent()

    val ALL_COLUMNS_NAMES = Column.values().map{it.columnName}.toTypedArray()


}