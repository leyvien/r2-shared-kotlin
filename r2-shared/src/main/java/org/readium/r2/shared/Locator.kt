/*
 * Module: r2-shared-kotlin
 * Developers: Aferdita Muriqi, Mostapha Idoubihi, Paul Stoica
 *
 * Copyright (c) 2018. Readium Foundation. All rights reserved.
 * Use of this source code is governed by a BSD-style license which is detailed in the
 * LICENSE file present in the project repository where this source code is maintained.
 */

package org.readium.r2.shared

import org.joda.time.DateTime
import org.json.JSONObject



/**
 * Locator model
 *
 * @var bookId: Long? - Book index in the database
 * @val publicationID: String -  Publication identifier
 * @val resourceIndex: Long - Index to the spine element of the book
 * @val resourceHref: String -  Reference to the spine element
 * @val resourceTitle: String - Title to the spine element of the book
 * @val location: Location - Location in the spine element
 * @val creationDate: Long - Datetime when the bookmark has been created
 * @var id: Long? - ID of the bookmark in database
 *
 * @fun toString(): String - Return a String description of the Locator
 */


open class Locator(val href:String,
              val created:Long,
              val title:String,
              val locations:Locations,
              val text:LocatorText?) {}

class LocatorText(var after:String? = null,
                  var before:String? = null,
                  var hightlight:String? = null)
    : JSONable {

        companion object {
            fun fromJSON(json: JSONObject): LocatorText {

                val location = LocatorText()
                if (json.has("before")) {
                    location.before = json.getString("before")
                }
                if (json.has("hightlight")) {
                    location.hightlight = json.getString("hightlight")
                }
                if (json.has("after")) {
                    location.after = json.getString("after")
                }

                return location
            }
        }

        override fun toJSON(): JSONObject {
            val json = JSONObject()

            before?.let {
                json.putOpt("before", before)
            }
            hightlight?.let {
                json.putOpt("hightlight", hightlight)
            }
            after?.let {
                json.putOpt("after", after)
            }

            return json
        }

        override fun toString(): String {
            var jsonString = """{"""

            if (before != null) {
                before.let { jsonString += """ "before": "$before" ,""" }
            }
            if (hightlight != null) {
                hightlight.let { jsonString += """ "before": "$hightlight" ,""" }
            }
            if (after != null) {
                after.let { jsonString += """ "after": "$after" ,""" }
            }
            jsonString += """}"""
            return jsonString
        }
}


/**
 * Location : Class that contain the different variables needed to localize a particular position
 *
 * @var id: Long? - Identifier of a specific fragment in the publication
 * @var cfi: String? - String formatted to designed a particular place in an Publication
 * @var cssSelector: String? - Css selector
 * @var xpath: String? - An xpath in the resource
 * @var progression: Double - A percentage ( between 0 and 1 ) of the progression in a Publication
 * @var position: Long - Index of a segment in the resource / synthetic page number!!??
 *
 */
class Locations(var id: Long? = null,
               var cfi: String? = null,             // 1 = highlight, annotation etc  = priority 3
               var cssSelector: String? = null,     // 2 =
               var xpath: String? = null,           // 2 =
               var progression: Double? = null,     // 3 = bookmark         = priority 1 (done)
               var position: Long? = null           // 4 = got page         = priority 2
) : JSONable {

    companion object {
        fun fromJSON(json: JSONObject): Locations {

            val location = Locations()
            if (json.has("id")) {
                location.id = json.getLong("id")
            }
            if (json.has("cfi")) {
                location.cfi = json.getString("cfi")
            }
            if (json.has("cssSelector")) {
                location.cssSelector = json.getString("cssSelector")
            }
            if (json.has("xpath")) {
                location.xpath = json.getString("xpath")
            }
            if (json.has("progression")) {
                location.progression = json.getDouble("progression")
            }
            if (json.has("position")) {
                location.position = json.getLong("position")
            }

            return location
        }
    }

    override fun toJSON(): JSONObject {
        val json = JSONObject()

        id?.let {
            json.putOpt("id", id)
        }
        cfi?.let {
            json.putOpt("cfi", cfi)
        }
        cssSelector?.let {
            json.putOpt("cssSelector", cssSelector)
        }
        xpath?.let {
            json.putOpt("xpath", xpath)
        }
        progression?.let {
            json.putOpt("progression", progression)
        }
        position?.let {
            json.putOpt("position", position)
        }

        return json
    }

    override fun toString(): String {
        var jsonString = """{"""
        if (id != null) {
            id.let { jsonString += """ "id": "$id" ,""" }
        }
        if (cfi != null) {
            cfi.let { jsonString += """ "cfi": "$cfi" ,""" }
        }
        if (cssSelector != null) {
            cssSelector.let { jsonString += """ "cssSelector": "$cssSelector" ,""" }
        }
        if (xpath != null) {
            xpath.let { jsonString += """ "xpath": "$xpath" ,""" }
        }
        progression.let { jsonString += """ "progression": "$progression" ,""" }
        position.let { jsonString += """ "position": "$position" """ }
        jsonString += """}"""
        return jsonString
    }
}