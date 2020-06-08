#!/usr/bin/env bash

# Decrypts the signing key in .github/codesigning.asc.enc
# Imports that key
# Uses .github/settings.xml and the release profile to deploy

echo "deploy.sh: Starting..."

(
    cd .github

    echo "Retrieving GPG Private KEY"
    gpg --quiet \
        --batch \
        --yes \
        --decrypt \
        --passphrase="${GPG_PASSPHRASE}" \
        --output codesigning.asc \
        codesigning.asc.gpg

    echo "Loading signing key"
    gpg --batch \
        --fast-import codesigning.asc
)

if test -z ${DEPLOY_PROJECTS}
then
    PROJECTS=""
    echo "Deploying Projects: all"
else
    PROJECTS="-pl ${DEPLOY_PROJECTS}"
    echo "Deploying Projects: $DEPLOY_PROJECTS"
fi

echo "Releasing..."
mvn ${PROJECTS} \
    --settings .github/settings.xml \
    -Dskip-Tests=true \
    -P release \
    -B \
    deploy

echo "deploy.sh: Done."
