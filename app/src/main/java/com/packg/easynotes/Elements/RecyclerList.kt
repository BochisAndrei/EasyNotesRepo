package com.packg.easynotes.Elements

object RecyclerList {
    var textNoteList : List<TextNote> = emptyList()
    var crossNoteList : List<CrossNote> = emptyList()
    var folderNoteList : List<Folder> = emptyList()

    var textNoteFavoriteList : List<TextNote> = emptyList()
    var crossNoteFavoriteList : List<CrossNote> = emptyList()

    var textNoteTrashedList : List<TextNote> = emptyList()
    var crossNoteTrashedList : List<CrossNote> = emptyList()

    //
    //
    //sort for home page
    //
    //
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
    //
    //
    //sort for allNotes page
    //
    //
    fun sortTextNoteForAllNotes(textNoteList: List<TextNote>): List<Element> {
        this.textNoteList = textNoteList
        val returnList = concatenate(textNoteList, crossNoteList)
        return sortList(returnList)
    }
    fun sortCrossNoteForAllNotes(crossNoteList : List<CrossNote>): List<Element> {
        this.crossNoteList = crossNoteList
        val returnList = concatenate(textNoteList, crossNoteList)
        return sortList(returnList)
    }
    //
    //
    //sort for favoriteNotes page
    //
    //
    fun sortTextNoteForFavoriteNotes(textNoteList: List<TextNote>): List<Element> {
        this.textNoteFavoriteList = textNoteList
        val returnList = concatenate(textNoteFavoriteList, crossNoteFavoriteList)
        return sortList(returnList)
    }
    fun sortCrossNoteForFavoriteNotes(crossNoteList : List<CrossNote>): List<Element> {
        this.crossNoteFavoriteList = crossNoteList
        val returnList = concatenate(textNoteFavoriteList, crossNoteFavoriteList)
        return sortList(returnList)
    }
    //
    //
    //sort for Trash page
    //
    //
    fun sortTextNoteForTrashedNotes(textNoteList: List<TextNote>): List<Element> {
        this.textNoteTrashedList = textNoteList
        val returnList = concatenate(textNoteTrashedList, crossNoteTrashedList)
        return sortList(returnList)
    }
    fun sortCrossNoteForTrashedNotes(crossNoteList : List<CrossNote>): List<Element> {
        this.crossNoteTrashedList = crossNoteList
        val returnList = concatenate(textNoteTrashedList, crossNoteTrashedList)
        return sortList(returnList)
    }

    //
    //
    //functions used to sort
    //
    //

    fun sortList(list: List<Element>): List<Element>{
        return list.sortedBy { it.createDate }
    }

    fun <T> concatenate(vararg lists: List<T>): List<T> {
        return listOf(*lists).flatten()
    }
}