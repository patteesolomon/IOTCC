import QtQuick 2.15
import QtQuick.Controls 2.15
import org.kde.kirigami 2.20 as Kirigami

Kirigami.ApplicationWindow {
    id: appWindow
    width: 640
    height: 480
    visible: true
    title: qsTr("Kirigami KDE Template")

    header: Kirigami.Header {
        title: appWindow.title
        menu: Kirigami.Menu {
            Kirigami.MenuItem {
                text: qsTr("About")
                onTriggered: appWindow.showAboutDialog()
            }
        }
    }

    footer: Kirigami.Footer {
        Label {
            text: qsTr("© 2025 Your Name")
            horizontalAlignment: Text.AlignHCenter
            width: parent.width
        }
    }

    sideBar: Kirigami.SideBar {
        Kirigami.NavigationList {
            // Example pages for navigation
            model: [
                { "text": qsTr("Home"), "iconName": "go-home" },
                { "text": qsTr("Settings"), "iconName": "settings" }
            ]
            onCurrentIndexChanged: {
                stackView.push(currentIndex === 0? homePage : settingsPage)
            }
        }
    }

    StackView {
        id: stackView
        anchors.fill: parent
        initialItem: homePage
    }

    Component {
        id: homePage
        Item {
            Label {
                anchors.centerIn: parent
                text: qsTr("Welcome to Kirigami!")
            }
        }
    }

    Component {
        id: settingsPage
        Item {
            Label {
                anchors.centerIn: parent
                text: qsTr("Settings Page")
            }
        }
    }
}
