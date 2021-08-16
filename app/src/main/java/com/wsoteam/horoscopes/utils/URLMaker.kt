package com.wsoteam.horoscopes.utils

import java.lang.Exception

object URLMaker {

    private const val DELIMETER = "/"

    //tt_key/sub1/sub2/sub3/sub4/sub5/sub6
    fun createLink(naming: String, url: String, gadid: String, afid: String): String {
        var sub_id_1 = ""
        var sub_id_2 = ""
        var sub_id_3 = ""
        var sub_id_4 = ""
        var sub_id_5 = ""
        var sub_id_6 = ""

        try {
            sub_id_1 = naming.split(DELIMETER)[1]
        } catch (ex: Exception) {

        }

        try {
            sub_id_2 = naming.split(DELIMETER)[2]
        } catch (ex: Exception) {

        }

        try {
            sub_id_3 = naming.split(DELIMETER)[3]
        } catch (ex: Exception) {

        }

        try {
            sub_id_4 = naming.split(DELIMETER)[4]
        } catch (ex: Exception) {

        }

        try {
            sub_id_5 = naming.split(DELIMETER)[5]
        } catch (ex: Exception) {

        }

        try {
            sub_id_6 = naming.split(DELIMETER)[6]
        } catch (ex: Exception) {

        }


        var url =
            "${url}?sub_id_1=$sub_id_1&sub_id_2=$sub_id_2&sub_id_3=$sub_id_3&sub_id_4=$sub_id_4&sub_id_5=$sub_id_5&sub_id_6=$sub_id_6&afid=$afid&gadid=$gadid"
        return url
    }

}