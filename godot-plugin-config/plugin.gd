@tool
extends EditorPlugin

func _enter_tree():
    add_custom_type("KotlinScript", "Script", preload("res://addons/kodotlin/kodotlin.gdextension"), null)

func _exit_tree():
    remove_custom_type("KotlinScript")