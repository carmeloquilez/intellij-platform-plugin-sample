package com.github.carmeloquilez.intellijplatformpluginsample.steps

import com.github.carmeloquilez.intellijplatformpluginsample.pages.DialogFixture
import com.github.carmeloquilez.intellijplatformpluginsample.pages.IdeaFrame
import com.github.carmeloquilez.intellijplatformpluginsample.pages.WelcomeFrame
import com.github.carmeloquilez.intellijplatformpluginsample.pages.dialog
import com.intellij.remoterobot.RemoteRobot
import com.intellij.remoterobot.search.locators.byXpath
import com.intellij.remoterobot.stepsProcessing.step
import com.intellij.remoterobot.utils.waitFor
import java.nio.file.Paths
import java.time.Duration

object SharedSteps {
    fun openProject(welcomeFrame: WelcomeFrame, projectName: String) = with(welcomeFrame) {
        step("Open project") {
            openProjectButton.click()
            dialog("Open File or Project") {
                val currentPath = Paths.get("").toAbsolutePath().toString()
                pathTextField.text = "$currentPath/src/test/testData/$projectName"
                button("OK").click()
            }
            trustProject(remoteRobot)
        }
    }

    fun trustProject(remoteRobot: RemoteRobot) {
        step("Accept Trust Project Dialog if it appears", Runnable {
            waitFor(Duration.ofSeconds(20)) {
                remoteRobot.findAll(
                    DialogFixture::class.java,
                    byXpath("//div[@class='MyDialog'][.//div[@text='Running startup activities...']]")
                ).isEmpty()
            }
            val idea: IdeaFrame = remoteRobot.find(IdeaFrame::class.java, Duration.ofSeconds(10))
            idea.dumbAware {
                try {
                    idea.find(DialogFixture::class.java, byXpath("Trust Project Button", "//div[@text.key='untrusted.project.dialog.trust.button']")).click()
                } catch (ignore: Throwable) {
                }
            }
        })
    }
}