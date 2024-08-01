package java.kyq;

/**
 * Description: 该类用于论证java的包名不能以“java.*”开头。执行该例子会抛出:
 * java.lang.SecurityException: Prohibited package name: java.kyq
 * 	at java.lang.ClassLoader.preDefineClass(ClassLoader.java:659)
 * 	at java.lang.ClassLoader.defineClass(ClassLoader.java:758)
 * 	at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
 * 	at java.net.URLClassLoader.defineClass(URLClassLoader.java:467)
 * 	at java.net.URLClassLoader.access$100(URLClassLoader.java:73)
 * 	at java.net.URLClassLoader$1.run(URLClassLoader.java:368)
 * 	at java.net.URLClassLoader$1.run(URLClassLoader.java:362)
 * 	at java.security.AccessController.doPrivileged(Native Method)
 * 	at java.net.URLClassLoader.findClass(URLClassLoader.java:361)
 * 	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
 * 	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
 * 	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
 * 	at sun.launcher.LauncherHelper.checkAndLoadMain(LauncherHelper.java:495)
 * Error: A JNI error has occurred, please check your installation and try again
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/9/12
 */
public class Test {

    public static void main(String args[]){

        System.out.println("1");
    }
}
