package com.abhishek.notes.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Abhishek Kumar on 13/08/20.
 * (c)2020 VMock. All rights reserved.
 */

@Parcelize
@Entity(tableName = "notes")
class Note() : Parcelable {

    @PrimaryKey(autoGenerate = true)
    private var id = 0

    @ColumnInfo(name = "title")
    private var title: String? = null

    @ColumnInfo(name = "content")
    private var content: String? = null

    @ColumnInfo(name = "timestamp")
    private var timestamp: String? = null

    override fun toString(): String {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}'
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (javaClass != other.javaClass) {
            return false
        }
        val note = other as Note
        return note.id == id && note.title == title && note.content == content
    }
}