# Kotlin/Wasm WASI Bindings Experiments

Uses new unsafe linear memory APSs `kotlin.wasm.unsafe.*` and annotation `kotlin.WasmImport`.

Bindings are generated from https://github.com/skuzmich/wasi-witx-kotlin-hacks/blob/kotlin-hacks/crates/witx-bindgen/src/lib.rs for `wasi_snapshot_preview1` version of WASI API.

# Testing

Uncomment and customize the `/sandbox` preopens path in `node_modules/wasi_snapshot_preview1/index.mjs` with the absolute path to you project root, for example  
```
preopens: {
    '/sandbox': '/home/user/workspace/kotlin-wasi-bindings-experiments'
},
```

Then run
```
./gradlew clean wasmTest
```
