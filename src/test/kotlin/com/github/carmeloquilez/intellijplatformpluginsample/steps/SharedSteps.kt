package com.github.carmeloquilez.intellijplatformpluginsample.steps

import com.github.carmeloquilez.intellijplatformpluginsample.pages.IdeaFrame
import com.github.carmeloquilez.intellijplatformpluginsample.pages.WelcomeFrame
import com.github.carmeloquilez.intellijplatformpluginsample.pages.dialog
import com.intellij.openapi.actionSystem.ex.ActionUtil.isDumbMode
import com.intellij.remoterobot.stepsProcessing.step
import com.intellij.remoterobot.utils.waitFor
import java.nio.file.Paths
import java.time.Duration

object SharedSteps {
    fun openProject(welcomeFrame: WelcomeFrame, projectName: String) = with(welcomeFrame) {
        step("Open project") {
            openProjectButton.click()
            Thread.sleep(3000)
//            dialog("Open File or Project") {
//                val currentPath = Paths.get("").toAbsolutePath().toString()
//                pathTextField.text = "$currentPath"
//                waitFor(Duration.ofSeconds(5)) { false }
//                pathTextField.text = "$currentPath/src"
//                waitFor(Duration.ofSeconds(5)) { false }
//                pathTextField.text = "$currentPath/src/test"
//                waitFor(Duration.ofSeconds(5)) { false }
//                pathTextField.text = "$currentPath/src/test/testData/$projectName"
//                waitFor(Duration.ofSeconds(10)) { button("OKK").isEnabled() }
//                button("OKK").click()
//            }
        }
    }

    fun openProjectView(idea: IdeaFrame) = with(idea) {
        step("Open Project View if it is closed") {
            if(projectViewLinks.size == 1) {
                projectViewLinks[0].click()
            }
        }
    }
}