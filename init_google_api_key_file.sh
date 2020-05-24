#!/usr/bin/env sh
(
  echo "{"
  for attr in type project_id private_key_id private_key client_email client_id auth_uri token_uri auth_provider_x509_cert_url; do
    echo "  \"$attr\": \"$(eval echo -e "\$$attr")\","
  done
  echo "  \"client_x509_cert_url\": \"$client_x509_cert_url\""
  echo "}"
) > "secret/portal-google-api.json"