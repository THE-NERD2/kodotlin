package dev.the_nerd2.kodotlin

@GodotClass("Sprite2D")
@Suppress("unused")
class Player {
    private var speed = 400.0
    private var angularSpeed = Math.PI
    @OverrideGodotAPI
    fun init() {
        godot_print("Hello, world!")
    }
    @OverrideGodotAPI
    fun physics_process(delta: Double) {
        val radians = (angularSpeed * delta).toFloat()
        rotate(radians)
    }
    @ImportGodotAPI
    private external fun rotate(radians: Float)
}