package com.project.data

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

enum class BT{
    ON,
    OFF,
    HACK
}

enum class StartUpExe{
    Code,
    OnRestart,
    OnShutdown
}

enum class InstallType{
    UserR,
    Hack,
    BTH
}

enum class CSA{
    ON,
    OFF
}

// AI Active
enum class AIA{
    ON,
    OFF
}

// emulation Monitor (Experimental)
enum class EmulationM{
    ON,
    OFF
}

enum class SystemMonitor{
    ON,
    OFF
}

// starts the pod net render if you have one
enum class NetRenderPod{
    ON,
    OFF,
    NONE
}

data class Config(
    @BsonId val id: ObjectId,
    val presetName : String,
    val optionBlutooth: Enum<BT>,
    val controlSystemActive: Enum<CSA>,
    val optionStartUpExe: Enum<StartUpExe>,
    val noAuth: Boolean,
    val installType: Enum<InstallType>,
    val AI_Active: Enum<AIA>,
    val EmulationOn: Enum<EmulationM>,
    val SystemMonitor: Enum<SystemMonitor>,
    val NetRenderMode: Enum<NetRenderPod>
)