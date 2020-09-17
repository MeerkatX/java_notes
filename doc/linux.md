# Linux

## 目录

`/etc` 配置文件所在目录 

`/proc` 存储的是当前内核运行状态的一系列特殊文件，用户可以通过这些文件查看有关系统硬件及当前正在运行进程的信息，甚至可以通过更改其中某些文件来改变内核的运行状态。(是伪文件系统，虚拟文件，存在内存中)

可以看到`pid` 进入`pid`文件后可以看到`fd`即文件描述符可以看到socket，IO这些

`/bin` 指令 `mkdir` `chmod`这些都放在这个文件夹下 Linux系统必备执行的命令。

`/opt` 存放第三方软件

`/root` 存放超级用户主目录

`/sys` 包含的文件用于获得硬件状态并反映内核看到的系统设备树。

`/tmp` 存放不同程序产生的临时文件，会被系统自动清理

`/usr` 存放用户应用程序和文件

`/usr/bin` 众多的应用程序

`/usr/sbin` 超级用户的一些管理程序

`/usr/include` Linux下开发和编译应用程序所需要的头文件

`/usr/lib` 常用的动态链接库和软件包的配置文件

`/usr/src` 源代码，Linux内核的源代码就放在这里

`/usr/local/bin` 本地增加的命令

`/usr/local/lib` 本地增加的库

`/mnt` 临时挂载的位置。临时将别的文件系统挂载再此文件下

`/media` 存放挂载和自动挂载设备的标准位置 如U盘

`/lib` 存放系统动态链接共享库

`/dev` 存放Linux系统使用的所有外部设备

`/boot` 存放Linux启动文件和内核

----

## 命令

### chmod

Linux/Unix 的文件调用权限分为三级 : 文件所有者（Owner）、用户组（Group）、其它用户（Other Users）

![img](linux.assets/file-permissions-rwx.jpg)

```bash
meerkatx@ubuntu:~$ chmod --- 目录、文件
```

R = 4

W = 2

X = 1

## bash

Bourne Again Shell

 `type` shell内置命令

```bash
# type [-tpa] name

meerkatx@ubuntu:~$ type cd
cd is a shell builtin # 内置命令
meerkatx@ubuntu:~$ type ls
ls is aliased to `ls --color=auto' #列出ls最主要使用情况

meerkatx@ubuntu:~$ type -a ls 
ls is aliased to `ls --color=auto'
ls is /usr/bin/ls
ls is /bin/ls
```

`history` 命令历史记录 持久化到 `~/.bash_history` 中

转义 `\` 

转义加回车就可以到下一行：

```bash
meerkatx@ubuntu:~$ type -a \
> ls
```

### 变量：

> 变量

echo 在变量前面加上 `$`

```bash
meerkatx@ubuntu:~$ echo $PATH
/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin
meerkatx@ubuntu:~$ echo $HOME
/home/meerkatx
```

设置，修改变量 用 `=`

```bash
meerkatx@ubuntu:~$ echo $myname #当没有这个变量时是空白输出

meerkatx@ubuntu:~$ myname = meerkatx # 等于号要紧跟变量，不然就变成命令了
myname: command not found
meerkatx@ubuntu:~$ myname=meerkatx
meerkatx@ubuntu:~$ echo $myname
meerkatx
meerkatx@ubuntu:~$ unset myname # 取消变量
meerkatx@ubuntu:~$ echo $myname

```

变量有一系列设置规则：鸟哥Linux私房菜301页

```bash
meerkatx@ubuntu:~$ myname=meerkatx
meerkatx@ubuntu:~$ echo $myname
meerkatx
meerkatx@ubuntu:~$ myname=$myname:hello
meerkatx@ubuntu:~$ echo $myname
meerkatx:hello
meerkatx@ubuntu:~$ myname="$myname"hello
meerkatx@ubuntu:~$ echo $myname
meerkatx:hellohello
```

```bash
meerkatx@ubuntu:~$ bash # 进入子进程 在当前shell中再打开一个shell
meerkatx@ubuntu:~$ echo $myname # 父进程自定义变量无法在子进程使用

meerkatx@ubuntu:~$ exit
exit
meerkatx@ubuntu:~$ echo $myname
meerkatx:hellohello
# 可以通过 export 命令将变量变成环境变量，这样就能在子进程看到了
```

```bash
meerkatx@ubuntu:~$ uname -r
5.4.0-40-generic
meerkatx@ubuntu:~$ cd /lib/modules/`uname -r`/kernel #进入到内核
```

> 环境变量：

```bash
meerkatx@ubuntu:~$ env
SHELL=/bin/bash
SESSION_MANAGER=local/ubuntu:@/tmp/.ICE-unix/2018,unix/ubuntu:/tmp/.ICE-unix/2018
QT_ACCESSIBILITY=1
COLORTERM=truecolor
XDG_CONFIG_DIRS=/etc/xdg/xdg-ubuntu:/etc/xdg
XDG_MENU_PREFIX=gnome-
GNOME_DESKTOP_SESSION_ID=this-is-deprecated
GTK_IM_MODULE=ibus
QT4_IM_MODULE=ibus
GNOME_SHELL_SESSION_MODE=ubuntu
SSH_AUTH_SOCK=/run/user/1000/keyring/ssh
XMODIFIERS=@im=ibus
DESKTOP_SESSION=ubuntu
SSH_AGENT_PID=1910
GTK_MODULES=gail:atk-bridge
PWD=/home/meerkatx
LOGNAME=meerkatx
XDG_SESSION_DESKTOP=ubuntu
XDG_SESSION_TYPE=x11
GPG_AGENT_INFO=/run/user/1000/gnupg/S.gpg-agent:0:1
XAUTHORITY=/run/user/1000/gdm/Xauthority
GJS_DEBUG_TOPICS=JS ERROR;JS LOG
WINDOWPATH=2
HOME=/home/meerkatx
USERNAME=meerkatx
IM_CONFIG_PHASE=1
LANG=en_US.UTF-8
LS_COLORS=rs=0:di=01;34:ln=01;36:mh=00:pi=40;33:so=01;35:do=01;35:bd=40;33;01:cd=40;33;01:or=40;31;01:mi=00:su=37;41:sg=30;43:ca=30;41:tw=30;42:ow=34;42:st=37;44:ex=01;32:*.tar=01;31:*.tgz=01;31:*.arc=01;31:*.arj=01;31:*.taz=01;31:*.lha=01;31:*.lz4=01;31:*.lzh=01;31:*.lzma=01;31:*.tlz=01;31:*.txz=01;31:*.tzo=01;31:*.t7z=01;31:*.zip=01;31:*.z=01;31:*.dz=01;31:*.gz=01;31:*.lrz=01;31:*.lz=01;31:*.lzo=01;31:*.xz=01;31:*.zst=01;31:*.tzst=01;31:*.bz2=01;31:*.bz=01;31:*.tbz=01;31:*.tbz2=01;31:*.tz=01;31:*.deb=01;31:*.rpm=01;31:*.jar=01;31:*.war=01;31:*.ear=01;31:*.sar=01;31:*.rar=01;31:*.alz=01;31:*.ace=01;31:*.zoo=01;31:*.cpio=01;31:*.7z=01;31:*.rz=01;31:*.cab=01;31:*.wim=01;31:*.swm=01;31:*.dwm=01;31:*.esd=01;31:*.jpg=01;35:*.jpeg=01;35:*.mjpg=01;35:*.mjpeg=01;35:*.gif=01;35:*.bmp=01;35:*.pbm=01;35:*.pgm=01;35:*.ppm=01;35:*.tga=01;35:*.xbm=01;35:*.xpm=01;35:*.tif=01;35:*.tiff=01;35:*.png=01;35:*.svg=01;35:*.svgz=01;35:*.mng=01;35:*.pcx=01;35:*.mov=01;35:*.mpg=01;35:*.mpeg=01;35:*.m2v=01;35:*.mkv=01;35:*.webm=01;35:*.ogm=01;35:*.mp4=01;35:*.m4v=01;35:*.mp4v=01;35:*.vob=01;35:*.qt=01;35:*.nuv=01;35:*.wmv=01;35:*.asf=01;35:*.rm=01;35:*.rmvb=01;35:*.flc=01;35:*.avi=01;35:*.fli=01;35:*.flv=01;35:*.gl=01;35:*.dl=01;35:*.xcf=01;35:*.xwd=01;35:*.yuv=01;35:*.cgm=01;35:*.emf=01;35:*.ogv=01;35:*.ogx=01;35:*.aac=00;36:*.au=00;36:*.flac=00;36:*.m4a=00;36:*.mid=00;36:*.midi=00;36:*.mka=00;36:*.mp3=00;36:*.mpc=00;36:*.ogg=00;36:*.ra=00;36:*.wav=00;36:*.oga=00;36:*.opus=00;36:*.spx=00;36:*.xspf=00;36:
XDG_CURRENT_DESKTOP=ubuntu:GNOME
VTE_VERSION=6001
GNOME_TERMINAL_SCREEN=/org/gnome/Terminal/screen/24575598_4fc0_49e8_90d3_fe4b92e89695
INVOCATION_ID=71eeeea4318b4644b9ae1a6f86ce52ec
MANAGERPID=1732
CLUTTER_IM_MODULE=ibus
GJS_DEBUG_OUTPUT=stderr
LESSCLOSE=/usr/bin/lesspipe %s %s
XDG_SESSION_CLASS=user
TERM=xterm-256color
LESSOPEN=| /usr/bin/lesspipe %s
USER=meerkatx
GNOME_TERMINAL_SERVICE=:1.89
DISPLAY=:0
SHLVL=1
QT_IM_MODULE=ibus
XDG_RUNTIME_DIR=/run/user/1000
JOURNAL_STREAM=9:50798
XDG_DATA_DIRS=/usr/share/ubuntu:/usr/local/share/:/usr/share/:/var/lib/snapd/desktop
PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin
GDMSESSION=ubuntu
DBUS_SESSION_BUS_ADDRESS=unix:path=/run/user/1000/bus
OLDPWD=/lib/modules/5.4.0-40-generic/kernel/drivers
_=/usr/bin/env
```

或者用 `export`

`set`可以查看所有变量包含环境和自定义

`ps1`提示符设置

```bash
meerkatx@ubuntu:~$ PS1='[\u@\h \w \A #\#]\$' #这一堆是各种格式 比如\A 指显示时间
[meerkatx@ubuntu ~ 19:51 #92]$ls
apache-maven-3.6.3  Desktop    Downloads        Music     Public  Templates
Code                Documents  ideaIC-2020.1.2  Pictures  snap    Videos
[meerkatx@ubuntu ~ 19:51 #93]$
```

`read` 读取用户输入 -p 提示 -t 等待时间

```bash
meerkatx@ubuntu:~/Desktop$ read -p "please enter a word:" test
please enter a word:123456
meerkatx@ubuntu:~/Desktop$ echo $test
123456
```

`declare` 声明变量

`-a` 数组

`-i` 整型

`-x` 环境变量

`-r` 只读 

```bash
meerkatx@ubuntu:~/Desktop$ sum=100+200 # 默认是字符串
meerkatx@ubuntu:~/Desktop$ echo $sum
100+200
meerkatx@ubuntu:~/Desktop$ declare -i sum=100+200 # 这里声明为 int，就计算了值
meerkatx@ubuntu:~/Desktop$ echo $sum
300
```

`ulimit` 与文件系统和程序的限制关系

常用在`Netty` `NIO` 那块写过 `-u`

```bash
meerkatx@ubuntu:~/Desktop$ ulimit -a # 列出所有可限制数据的数值
core file size          (blocks, -c) 0
data seg size           (kbytes, -d) unlimited
scheduling priority             (-e) 0
file size               (blocks, -f) unlimited
pending signals                 (-i) 24173
max locked memory       (kbytes, -l) 65536
max memory size         (kbytes, -m) unlimited
open files                      (-n) 1024 # 通常服务器需要改这个 最大打开文件数（一个socket连接是一个文件）
pipe size            (512 bytes, -p) 8
POSIX message queues     (bytes, -q) 819200
real-time priority              (-r) 0
stack size              (kbytes, -s) 8192
cpu time               (seconds, -t) unlimited # 可用最大cpu 时间
max user processes              (-u) 24173 # 单一用户可以使用的最大进程数量
virtual memory          (kbytes, -v) unlimited
file locks                      (-x) unlimited
```

`alias` 别名

```bash
meerkatx@ubuntu:~/Desktop$ alias 
alias alert='notify-send --urgency=low -i "$([ $? = 0 ] && echo terminal || echo error)" "$(history|tail -n1|sed -e '\''s/^\s*[0-9]\+\s*//;s/[;&|]\s*alert$//'\'')"'
alias egrep='egrep --color=auto'
alias fgrep='fgrep --color=auto'
alias grep='grep --color=auto'
alias l='ls -CF'
alias la='ls -A'
alias ll='ls -alF'
alias ls='ls --color=auto' # 这同样也是用法
```

`history`  

感叹号 `!` 

```bash
meerkatx@ubuntu:~/Desktop$ !u # ！+ 指令 执行最后一个名字中首字母为u的指令    ！！是执行上一个指令
ulimit -a
core file size          (blocks, -c) 0
```

`source` 读入配置文件的命令

### bash特殊符号

|符号|	内容|
|---|---|
|#|	批注符号：这个最常被使用在 script 当中，视为说明！在后的数据均不运行|
|\	|跳脱符号：将『特殊字符或通配符』还原成一般字符|
|\||	管线 (pipe)：分隔两个管线命令的界定(后两节介绍)；|
|;|	连续命令下达分隔符：连续性命令的界定 (注意！与管线命令并不相同)|
|~|	用户的家目录|
|$	|取用变量前导符：亦即是变量之前需要加的变量取代值|
|&	|工作控制 (job control)：将命令变成背景下工作|
|!	|逻辑运算意义上的『非』 not 的意思！|
|/	|目录符号：路径分隔的符号|
|>, >>	|数据流重导向：输出导向，分别是『取代』与『累加』|
|<, <<	|数据流重导向：输入导向|
|' '	|单引号，不具有变量置换的功能|
|" "	|具有变量置换的功能！|
|\` \`|	两个『 ` 』中间为可以先运行的命令，亦可使用 $( )|
|( )	|在中间为子 shell 的起始与结束|
|{ }	|在中间为命令区块的组合！|
### bash逻辑

从左往右执行

`;` cmd1; cmd2 执行完cmd1 之后执行 cmd2 没有关联

`&&` cmd1 && cmd2     如果cmd1正确再运行cmd2 （与）

`||` cmd1 || cmd2      如果cmd1 正确，就不运行cmd2 如果 cmd1 错误，就运行cmd2 （或）

```bash
meerkatx@ubuntu:~/Desktop$ sudo ls /tmp/abc || mkdir /tmp/abc # 用 || 来判断有没有文件并且进行新建
ls: cannot access '/tmp/abc': No such file or directory
```

判断一个目录是否存在，存在显示exist

```bash
meerkatx@ubuntu:~/Desktop$ ls /tmp/abc && echo "exist" || echo "not exist"
exist
```

### pipe

常用管道命令` cut grep sort wc uniq tee tr col join paste expand split xargs`

`|`  cmd1 | cmd2 表示将前一个指令的正确信息作为输入到后一个命令

```bash
meerkatx@ubuntu:~/Desktop$ ps -ef
UID          PID    PPID  C STIME TTY          TIME CMD
root           1       0  0 09:43 ?        00:00:19 /sbin/init auto noprompt
root           2       0  0 09:43 ?        00:00:00 [kthreadd]
root           3       2  0 09:43 ?        00:00:00 [rcu_gp]
# ...

# 例如常用的 ps -ef | grep
meerkatx@ubuntu:~/Desktop$ ps -ef | grep redis
meerkatx    3463    1808  0 10:35 ?        00:00:00 redis-server 127.0.0.1:6379
meerkatx    3469    2934  0 10:35 pts/1    00:00:00 grep --color=auto redis
```

#### grep 和 cut

`$?` 变量，回传码 0 表示成功 

>  `grep` 选取命令

可以结合正则表达式

`grep [-acinv] [--color=auto] ‘查找字符串’ 文件名`

-a 二进制文件以text查

-c 计算找到的次数

-i 忽略大小写

-n 输出行号

-v 反向选择

```bash
meerkatx@ubuntu:~/Desktop$ grep 'debian' ~/.bashrc
if [ -z "${debian_chroot:-}" ] && [ -r /etc/debian_chroot ]; then
    debian_chroot=$(cat /etc/debian_chroot)
    PS1='${debian_chroot:+($debian_chroot)}\[\033[01;32m\]\u@\h\[\033[00m\]:\[\033[01;34m\]\w\[\033[00m\]\$ '
    PS1='${debian_chroot:+($debian_chroot)}\u@\h:\w\$ '
    PS1="\[\e]0;${debian_chroot:+($debian_chroot)}\u@\h: \w\a\]$PS1"
meerkatx@ubuntu:~/Desktop$ grep -c 'debian' ~/.bashrc
5
meerkatx@ubuntu:~/Desktop$ grep -n 'debian' ~/.bashrc
34:if [ -z "${debian_chroot:-}" ] && [ -r /etc/debian_chroot ]; then
35:    debian_chroot=$(cat /etc/debian_chroot)
60:    PS1='${debian_chroot:+($debian_chroot)}\[\033[01;32m\]\u@\h\[\033[00m\]:\[\033[01;34m\]\w\[\033[00m\]\$ '
62:    PS1='${debian_chroot:+($debian_chroot)}\u@\h:\w\$ '
69:    PS1="\[\e]0;${debian_chroot:+($debian_chroot)}\u@\h: \w\a\]$PS1"
```

> `cut` 将信息某一段切出来

`cut -d ‘分割符’ -f  第几段` 

`cut -c 字符范围` 

```bash
meerkatx@ubuntu:~/Desktop$ echo $PATH # 其中第5个是 /sbin
/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin
meerkatx@ubuntu:~/Desktop$ echo $PATH | cut -d ':' -f 5
/sbin
meerkatx@ubuntu:~/Desktop$ echo $PATH | cut -d ':' -f 3,5 # 多段可以用逗号
/usr/sbin:/sbin
meerkatx@ubuntu:~/Desktop$ echo $PATH | cut -c 20- # 表示第20个字符后
r/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin
```

#### `wc sort uniq`

`wc [-lwm]` -l line -w word -m 字符

```bash
meerkatx@ubuntu:~/Desktop$ wc ~/.bashrc # 行 单词 字符
 117  518 3771 /home/meerkatx/.bashrc
meerkatx@ubuntu:~/Desktop$ cat ~/.bashrc | wc
    117     518    3771
```

```bash
meerkatx@ubuntu:~/Desktop$ last | cut -d ' ' -f1 | sort # sort 排序

meerkatx
meerkatx
meerkatx
reboot
reboot
reboot
reboot

meerkatx@ubuntu:~/Desktop$ last | cut -d ' ' -f1 | sort | uniq # 取唯一

meerkatx
reboot
wtmp
```

```bash
meerkatx@ubuntu:~/Desktop$ last | tee last.list | cut -d " " -f1 
# tee 双重重定向 到文件和屏幕(stdout)
```

```bash
# stdin 和 stdout 可以用 - 代替

meerkatx@ubuntu:~/Desktop$ tar -cvf - /home | tar -xvf 
```

---

## Vim

> 一般模式

常用快捷键：

`gg` 移动到最上

`shift + g` 移动到最下

`/word` 查找 `n`查找下一个 `shift + n (即大写N)` 查找上一个

`u` 相当于撤销

`ctrl + r` 相当于重做

> 编辑模式

`i` 插入

`r` 替换

> 命令模式

`:w` 写入 保存

`:wq` 强制写入后退出

`:q!` 强制退出

`set nu` 显示行号

