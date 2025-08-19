#ifndef KIRIGAMIWINDOW_H
#define KIRIGAMIWINDOW_H
#include QtQuick
#include org.kde.kirigami as Kirigami
class KirigamiWindow
{
public:
    KirigamiWindow();
};
void WindowNew(){
        Kirigami.ApplicationWindow {
           title: "Single Page"
           width: 500
           height: 200

           Kirigami.PageRow {
            anchors.fill: parent

            Kirigami.Page {
                  id: mainPage
                  anchors.fill: parent
                  Rectangle {
                    anchors.fill: parent
                    color: "lightblue"
                }
            }
        }
    }
}
#endif // KIRIGAMIWINDOW_H
