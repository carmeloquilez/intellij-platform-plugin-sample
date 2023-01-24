package com.github.carmeloquilez.intellijplatformpluginsample.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.github.carmeloquilez.intellijplatformpluginsample.services.ClassService
import org.jetbrains.annotations.NotNull

class ViewAccessibleFieldsAction : AnAction() {

    private val stringBuilder = StringBuilder()
    private var project: Project? = null
    private var editor: Editor? = null
    private var psiFile: PsiFile? = null

    override fun update(@NotNull event: AnActionEvent) {
        val project: Project? = event.project
        val editor = event.getData(CommonDataKeys.EDITOR)
        val psiFile = event.getData(CommonDataKeys.PSI_FILE)

        val visible = project != null && editor != null && psiFile != null
        if (visible) {
            this.project = project
            this.editor = editor
            this.psiFile = psiFile
        }

        event.presentation.isEnabledAndVisible = visible
    }

    override fun actionPerformed(@NotNull event: AnActionEvent) {
        checkRequiredData()

        val project = project as Project
        val editor = editor as Editor
        val psiFile = psiFile as PsiFile

        stringBuilder.clear()

        val offset = editor.caretModel.offset
        val element = psiFile.findElementAt(offset)

        val containingClass = PsiTreeUtil.getParentOfType(element, PsiClass::class.java)
        if (containingClass != null) {
            val classContext = ClassService.getClassContext(containingClass)
            classContext.attributes.joinTo(stringBuilder)
        }
        Messages.showMessageDialog(project, stringBuilder.toString(), "Class Attributes", null)
    }

    private fun checkRequiredData() {
        if (project == null || editor == null || psiFile == null)
            throw IllegalStateException("There is some required data null")
    }
}