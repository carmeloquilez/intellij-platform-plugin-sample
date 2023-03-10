// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.github.carmeloquilez.intellijplatformpluginsample.pages

import com.intellij.remoterobot.RemoteRobot
import com.intellij.remoterobot.data.RemoteComponent
import com.intellij.remoterobot.fixtures.*
import com.intellij.remoterobot.search.locators.byXpath
import java.time.Duration

fun RemoteRobot.welcomeFrame(function: WelcomeFrame.()-> Unit) {
    find(WelcomeFrame::class.java, Duration.ofSeconds(10)).apply(function)
}

@FixtureName("Welcome Frame")
@DefaultXpath("type", "//div[@class='FlatWelcomeFrame']")
class WelcomeFrame(remoteRobot: RemoteRobot, remoteComponent: RemoteComponent) : CommonContainerFixture(remoteRobot, remoteComponent) {
    val createNewProjectLink
        get() = actionLink(byXpath("New Project","//div[(@class='MainButton' and @text='New Project') or (@accessiblename='New Project' and @class='JButton')]"))
    val moreActions
        get() = button(byXpath("More Action", "//div[@accessiblename='More Actions']"))

    val heavyWeightPopup
        get() = remoteRobot.find(ComponentFixture::class.java, byXpath("//div[@class='HeavyWeightWindow']"))

    val openProjectButton
        get() = actionLink(byXpath("Open",
            "//div[(@accessiblename='Open' and @accessiblename.key='action.Tabbed.WelcomeScreen.OpenProject.text' and @class='JBOptionButton' and @text='Open' and @text.key='action.Tabbed" +
                    ".WelcomeScreen.OpenProject.text') or @defaulticon='open.svg']"))

    val trustProjectButton
        get() = remoteRobot.find(ComponentFixture::class.java, byXpath("//div[@text.key='untrusted.project.dialog.trust.button']"))
}