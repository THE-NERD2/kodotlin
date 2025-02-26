#!/bin/bash

# Build Rust
cd rust
cargo build --release
cd ..

# Build C++
cd c++
scons arch=arm64 platform=linux target=release