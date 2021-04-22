const {spawn} = require('child_process')
const {readFileSync, existsSync} = require('fs')

const [moduleName, servers, eurekas] = process.argv.slice(2)

start(moduleName, servers.split(','), (eurekas || servers).split(','))

function start(moduleName, serverList, eurekaList) {
    const env = {
        ...process.env,
        ...parseEnv('env.local')
    }

    const zones = eurekaList.map(s => `http://${s}/eureka/`).join(',')
    console.log(`[${moduleName}] setting zones to ${zones}`)
    serverList.forEach(s => {
        const [host, port] = s.split(':')
        console.log(`[${moduleName}] booting ${s}`)
        const c = spawn(
            process.platform === 'win32' ? '.\\gradlew.bat' : './gradlew',
            [
                `:${moduleName}:bootRun`,
                '--args',
                `'--server.port=${port}' '--eureka.client.service-url.defaultZone=${zones}'`
                + (host ? ` '--eureka.instance.hostname=${host}'` : ''),
                '--parallel',
                '-q'
            ], {
                env
            })
        c.stderr.on('data', data => console.log(`[${moduleName}][${s}] `, data.toString()))
        c.stdout.on('data', data => {
            const msg = data.toString()
            if (msg.includes('Exception')) console.log(`[${moduleName}][${s}] `, msg)
        })
    })
}

function parseEnv(path) {
    if (!existsSync(path)) return {}
    const envRecords = readFileSync(path).toString().split(/\r\n|\r|\n/)
    const env = {}
    envRecords.forEach(e => {
        const i = e.indexOf('=')
        env[e.substring(0, i)] = e.substring(i + 1)
    })
    return env
}