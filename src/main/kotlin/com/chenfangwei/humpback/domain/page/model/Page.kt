package com.chenfangwei.humpback.domain.page.model

import com.chenfangwei.humpback.domain.page.exception.InvalidPageOperationException
import com.chenfangwei.humpback.share.factory.generateUUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*


@Document(collection = "page")
class Page(val creatorId: String, val spaceId: String) {
    @Id
    var id: String? = null

    var title: String? = null

    var blocks: ArrayList<PageBlock>? = null

    @CreatedDate
    private val createdDate: Date? = null

    fun addBlockContent(content: String, previousBlockId: String) {
        val lastBlockIndex = blocks!!.indexOfFirst { b -> b.id == previousBlockId }
        if (lastBlockIndex < 0) {
            throw InvalidPageOperationException("Previous block not found")

        }
        val block = PageBlock(generateUUID(), content)
        addBlock(block, lastBlockIndex)
    }

    fun addBlockContent(content: String) {
        addBlock(PageBlock(generateUUID(), content), null)
    }

    fun addBlock(block: PageBlock, lastBlockIndex: Int?) {
        if (blocks == null) {
            blocks = arrayListOf(block)
            return
        }
        if (lastBlockIndex != null) {
            blocks!!.add(lastBlockIndex + 1, block)
        } else {
            blocks!!.add(block)
        }
    }
}