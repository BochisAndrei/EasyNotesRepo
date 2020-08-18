package com.packg.easynotes.Elements

object RecyclerList {
    var textNoteList : List<TextNote> = emptyList()
    var crossNoteList : List<CrossNote> = emptyList()
    var folderNoteList : List<Folder> = emptyList()

    fun sortTextNote(textNoteList: List<TextNote>): List<Element> {
        this.textNoteList = textNoteList
        val returnList = concatenate(textNoteList, crossNoteList, folderNoteList)
        return sortList(returnList)
    }
    fun sortCrossNote(crossNoteList : List<CrossNote>): List<Element> {
        this.crossNoteList = crossNoteList
        val returnList = concatenate(textNoteList, crossNoteList, folderNoteList)
        return sortList(returnList)
    }
    fun sortFolder(folderNoteList : List<Folder>): List<Element> {
        this.folderNoteList = folderNoteList
        val returnList = concatenate(textNoteList, crossNoteList, folderNoteList)
        return sortList(returnList)
    }

    fun sortList(list: List<Element>): List<Element>{
        return list.sortedBy { it.createDate }
    }

    fun <T> concatenate(vararg lists: List<T>): List<T> {
        return listOf(*lists).flatten()
    }
}