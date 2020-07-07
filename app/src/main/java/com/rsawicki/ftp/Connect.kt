package com.rsawicki.ftp

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jcraft.jsch.*
import kotlinx.android.synthetic.main.connect.*
import java.io.*
import java.util.*


class Connect:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connect)

        val folder = filesDir
        val f = File(folder, "WSO")
        f.mkdir()
        val folderPath = folder.toString()
        conn_connect.setOnClickListener {
            var inputPort_s = connPort.text.toString()
            var inputIP = connIP.text.toString()
            var inputPort = inputPort_s.toInt()
            var inputLogin = connLogin.text.toString()
            var inputPassword = connPassword.text.toString()
            SshTask().execute(inputLogin, inputPassword, inputIP)
        }
    }

    private inner class SshTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String): String? {
            print("test" + params[0])
            val output = getData(username = params[0], password = params[1], hostname = params[2])
            val intent = Intent(this@Connect,ShowData::class.java)
            intent.putExtra("data",output)
            startActivity(intent)
            return output
        }
    }
    fun getData(username: String,
                             password: String,
                             hostname: String,
                             port: Int = 22): String {
        val jsch = JSch()
        val session = jsch.getSession(username, hostname, port)
        session.setPassword(password)

        val properties = Properties()
        properties["StrictHostKeyChecking"] = "no"
        session.setConfig(properties)

        session.connect()

        val sshChannel = session.openChannel("exec") as ChannelExec
        val outputStream = ByteArrayOutputStream()
        sshChannel.outputStream = outputStream

        sshChannel.setCommand("cd WSO")
        sshChannel.connect()

        Thread.sleep(1_000)
        sshChannel.disconnect()

        val channel: Channel = session.openChannel("sftp")
        channel.connect();
        val sftpChannel = channel as ChannelSftp


        sftpChannel.get("WSO/output.txt", "/data/user/0/com.rsawicki.ftp/files/output.txt")
        sftpChannel.exit()

        session.disconnect()
        println("reading.... " + "/data/user/0/com.rsawicki.ftp/files/output.txt")
        val file = File("/data/user/0/com.rsawicki.ftp/files/output.txt").readText(Charsets.UTF_8)

//        return "48\n19\n44\n18\n46\n44\n21\n31\n44\n58\n46\n44\n21\n37\n21\n17\n14\n18\n17\n21\n46\n44\n102\n19\n58\n24\n44\n107\n9\n105\n105\n105\n19\n111"
        return file
    }
}
