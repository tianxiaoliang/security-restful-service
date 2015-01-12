#!/bin/bash
baseDirForScriptSelf=$(cd "$(dirname "$0")"; pwd) 
PRJDIR=$(dirname $baseDirForScriptSelf)

echo "please input root password to restore db"
SQL=`cat $PRJDIR/src/main/resources/dbdeploy/create.sql`
mysql -uroot -p -e "$SQL"
echo "please input root password to restore tables"
mysql -u root -p template < $PRJDIR/src/main/resources/dbdeploy/tables.sql
