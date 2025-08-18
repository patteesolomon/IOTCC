cmake_minimum_required(VERSION 3.16)
project(KirigamiTemplate LANGUAGES CXX)

find_package(Qt6 REQUIRED COMPONENTS Quick)
find_package(KF6Kirigami2 REQUIRED)

qt_standard_project_setup()

qt_add_executable(app_KirigamiTemplate
    main.cpp
    qml.qrc
)

target_link_libraries(app_KirigamiTemplate
    Qt6::Quick
    KF6::Kirigami2
)

# Enable automatic QML imports
set_target_properties(app_KirigamiTemplate PROPERTIES
    AUTOMOC ON
    AUTORCC ON
    AUTOUIC ON
)