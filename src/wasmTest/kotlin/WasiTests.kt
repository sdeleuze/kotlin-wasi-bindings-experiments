package kotlinx.wasi

import kotlin.test.*
import kotlin.time.toDuration
import kotlin.time.DurationUnit

class WasiTests {

    @Test
    fun testGetCommandLineArgumentsSize() {
        val (size, size_buffer) = args_sizes_get()
        assertEquals(size, 2)
        assertEquals(size_buffer, 28)
    }

    @Test
    fun testGetCommandLineArguments() {
        val args: List<String> = args_get()
        assertEquals(args, listOf("argument1", "аргумент2"))
    }

    @Test
    fun testGetEnvironmentVariablesSize() {
        val (size, size_buffer) = environ_sizes_get()
        assertEquals(size, 2)
        assertEquals(size_buffer, 48)
    }

    @Test
    fun testGetEnvironmentVariables() {
        val environ: Map<String, String> = environ_get()
        assertEquals(
            environ,
            mapOf(
                "PATH" to "/usr/local/bin:/usr/bin",
                "HOME" to "/Users/이준"
            )
        )
    }

    @Test
    fun testPrintln() {
        kotlinx.wasi.println("Hello!")
    }

    @Test
    fun testCreateDirectory() {
       path_create_directory(3, "build/testDir")
    }

    @Test
    fun testCreateFile() {
        path_open(3, 0, "build/testFile", OFLAGS.CREAT, 0, 0, 0)
    }

    @Test
    fun testGetDirectoryName() {
        kotlinx.wasi.println(fd_prestat_dir_name(3))
    }

    @Test
    fun testGetTime() {
        val resolution = clock_res_get(Clockid.REALTIME)
        val duration: Timestamp = clock_time_get(Clockid.REALTIME, resolution)
        kotlinx.wasi.println("Resolution ${resolution}")
        kotlinx.wasi.println("Duration ${duration.toDuration(DurationUnit.NANOSECONDS)}")
    }

    @Test
    fun testListFiles() {
        for (file in fd_readdir(3)) {
            kotlinx.wasi.println(file)
        }
    }
}

@JsFun("""
await (async () => {
    let { wasi } = await import('wasi_snapshot_preview1');
    return () => { wasi.initialize(wasmInstance); }
})()
""")
external fun initializeNodeWasi()

fun main() {
    initializeNodeWasi()
}