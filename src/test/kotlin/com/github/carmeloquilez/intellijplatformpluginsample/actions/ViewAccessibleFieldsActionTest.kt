// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.github.carmeloquilez.intellijplatformpluginsample.actions

import com.automation.remarks.junit5.Video
import com.github.carmeloquilez.intellijplatformpluginsample.pages.*
import com.github.carmeloquilez.intellijplatformpluginsample.steps.SharedSteps
import com.intellij.remoterobot.RemoteRobot
import com.intellij.remoterobot.search.locators.byXpath
import com.intellij.remoterobot.stepsProcessing.step
import com.intellij.remoterobot.utils.keyboard
import com.intellij.remoterobot.utils.waitFor
import com.intellij.remoterobot.utils.waitForIgnoringError
import com.github.carmeloquilez.intellijplatformpluginsample.utils.RemoteRobotExtension
import com.github.carmeloquilez.intellijplatformpluginsample.utils.StepsLogger
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.assertj.swing.core.MouseButton
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.awt.event.KeyEvent.*
import java.time.Duration.ofMinutes
import java.time.Duration.ofSeconds

@ExtendWith(RemoteRobotExtension::class)
@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class ViewAccessibleFieldsActionTest : BasePlatformTestCase() {
    init {
        StepsLogger.init()
    }

    @BeforeEach
    fun waitForIde(remoteRobot: RemoteRobot) {
        waitForIgnoringError(ofMinutes(3)) { remoteRobot.callJs("true") }
    }

//    @AfterEach
//    fun closeProject(remoteRobot: RemoteRobot) = with(remoteRobot) {
//        idea {
//            if (remoteRobot.isMac()) {
//                keyboard {
//                    hotKey(VK_SHIFT, VK_META, VK_A)
//                    enterText("Close Project")
//                    enter()
//                }
//            } else {
//                menuBar.select("File", "Close Project")
//            }
//        }
//    }

    @Test
    @Video
    fun viewAccessibleFieldsActionsTest(remoteRobot: RemoteRobot) = with(remoteRobot) {
        welcomeFrame {
            SharedSteps.openProject(this, "java-maven")
        }
//        idea {
//            waitFor(ofMinutes(5)) { isDumbMode().not() }
//            closeTipOfTheDay(remoteRobot)
//            SharedSteps.openProjectView(this)
//            step("Open MainClass file") {
//                with(projectViewTree) {
//                    if (hasText("MainClass")) {
//                        findText("MainClass").doubleClick()
//                    } else {
//                        if (hasText("src").not()) {
//                            findText(projectName).doubleClick()
//                            waitFor { hasText("src") }
//                        }
//                        if (hasText("main").not()) {
//                            findText("src").doubleClick()
//                            waitFor { hasText("main") }
//                        }
//                        if (hasText("java").not()) {
//                            findText("main").doubleClick()
//                            waitFor { hasText("java") }
//                        }
//                        if (hasText("org.example").not()) {
//                            findText("java").doubleClick()
//                            waitFor { hasText("org.example") }
//                        }
//                        if (hasText("MainClass").not()) {
//                            findText("org.example").doubleClick()
//                            waitFor { hasText("java") }
//                        }
//                    }
//                }
//            }
//            findText("MainClass").doubleClick()
//            with(textEditor()) {
//                step("Write a code") {
//                    Thread.sleep(1_000)
//                    editor.findText("sampleMethod").click(MouseButton.RIGHT_BUTTON)
//                    actionMenuItem("View Accessible Fields").click()
//                    acceptDialog(remoteRobot)
//                }
//            }
//        }
    }

    private fun closeTipOfTheDay(remoteRobot: RemoteRobot) {
        step("Close Tip of the Day if it appears", Runnable {
            waitFor(ofSeconds(20)) {
                remoteRobot.findAll(
                    DialogFixture::class.java,
                    byXpath("//div[@class='MyDialog'][.//div[@text='Running startup activities...']]")
                ).isEmpty()
            }
            val idea: IdeaFrame = remoteRobot.find(IdeaFrame::class.java, ofSeconds(10))
            idea.dumbAware {
                try {
                    idea.find(DialogFixture::class.java, DialogFixture.byTitle("Tip of the Day")).button("Close").click()
                } catch (ignore: Throwable) {
                }
            }
        })
    }

    private fun trustProject(remoteRobot: RemoteRobot) {
        step("Accept Trust Project Dialog if it appears", Runnable {
            waitFor(ofSeconds(20)) {
                remoteRobot.findAll(
                    DialogFixture::class.java,
                    byXpath("//div[@class='MyDialog'][.//div[@text='Running startup activities...']]")
                ).isEmpty()
            }
            val idea: IdeaFrame = remoteRobot.find(IdeaFrame::class.java, ofSeconds(10))
            idea.dumbAware {
                try {
                    idea.find(DialogFixture::class.java, byXpath("Trust Project Button", "//div[@text.key='untrusted.project.dialog.trust.button']")).click()
                } catch (ignore: Throwable) {
                }
            }
        })
    }

    private fun acceptDialog(remoteRobot: RemoteRobot) {
        step("Accept Dialog", Runnable {
            val idea: IdeaFrame = remoteRobot.find(IdeaFrame::class.java, ofSeconds(10))
            idea.dumbAware {
                try {
                    idea.find(DialogFixture::class.java, byXpath("Class Attributes", "//div[@text.key='button.ok']")).click()
                } catch (ignore: Throwable) {
                }
            }
        })
    }

    override fun getTestDataPath() = "src/test/testData"
}