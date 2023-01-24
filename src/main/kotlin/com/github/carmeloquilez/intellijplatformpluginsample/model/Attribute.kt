package com.github.carmeloquilez.intellijplatformpluginsample.model

class Attribute(val name : String, val classType: String, val fullClassType: String) {
    override fun toString(): String {
        return "Attribute(name='$name', classType='$classType', fullClassType='$fullClassType')"
    }
}