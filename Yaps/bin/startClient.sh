cygwin=false
case "`uname`" in
CYGWIN*) cygwin=true;;
esac

JAVA=$JAVA_HOME/bin/java
DEPLOY_DIR=../build

CLASSPATH=$DEPLOY_DIR/client.jar:$DEPLOY_DIR/common.jar:$DEPLOY_DIR$/services-ejb-client.jar:$GLASSFISH_HOME/lib/gf-client.jar"
if $cygwin; then
CLASSPATH="CLASSPATH=$DEPLOY_DIR/client.jar;$DEPLOY_DIR/common.jar;$DEPLOY_DIR$/services-ejb-client.jar;$GLASSFISH_HOME/lib/gf-client.jar"
fi

$JAVA -classpath "$CLASSPATH" com.yaps.petstore.client.ui.Menu
