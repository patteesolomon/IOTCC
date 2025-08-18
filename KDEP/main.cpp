#include <QGuiApplication>
#include <QQmlApplicationEngine>
#include <KLocalizedContext>

int main(int argc, char **argv)
{
    QGuiApplication app(argc, argv);

    QQmlApplicationEngine engine;

    // Set the translation context for KDE Kirigami if needed
    KLocalizedContext locContext;
    locContext.setTranslationDomain("kirigami");
    engine.rootContext()->setContextObject(&locContext);

    engine.load(QUrl(QStringLiteral("qrc:/main.qml")));
    if (engine.rootObjects().isEmpty())
        return -1;

    return app.exec();
}