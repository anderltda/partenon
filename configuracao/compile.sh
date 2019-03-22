#!/bin/sh
WORKING_DIR='/Users/anderson/Java/workspaces/myeclipse'
echo "Iniciando o processo de build"

project_list=(bootstraptheme commons/core foundation/core commons/entity commons/service foundation/web commons/web seguranca/core seguranca/web cadastro/core cadastro/web monitoramento/core monitoramento/web project)

count=${#project_list[@]}

mvn clean -U install

for (( i=0;i<$count;i++)); do
  PROJECT_NAME=${project_list[${i}]};
  cd $WORKING_DIR/$PROJECT_NAME/
  echo $WORKING_DIR/$PROJECT_NAME/ 
  mvn clean -U install eclipse:eclipse 
done



