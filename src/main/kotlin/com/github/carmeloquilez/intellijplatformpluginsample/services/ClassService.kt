package com.github.carmeloquilez.intellijplatformpluginsample.services

import com.github.carmeloquilez.intellijplatformpluginsample.model.Attribute
import com.intellij.psi.*
import com.github.carmeloquilez.intellijplatformpluginsample.model.ClassContext

object ClassService {
    fun getClassContext(psiClass: PsiClass): ClassContext {
        val attributes = ArrayList<Attribute>()
        for (variable in psiClass.allFields) {
            attributes.add(Attribute(variable.name, variable.type.internalCanonicalText, variable.type.canonicalText))
        }
        return ClassContext(attributes)
    }
}