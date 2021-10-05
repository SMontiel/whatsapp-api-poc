# WhatsApp API PoC

Como primer paso se tiene que crear una cuenta en Twilio o pedir acceso al ambiente de pruebas.
Después, ir a `https://console.twilio.com/?frameUrl=%2Fconsole%3Fx-target-region%3Dus1` y copiar
el `Account SID` y `Auth Token` (necesarios para comunicarnos con Twilio SDK).

Para registrar un número en `Twilio Sandbox for WhatsApp` necesitas registrar
el siguiente número en tus contactos: `+14155238886`. Después envía al número
registrado este mensaje: `join before-tea` desde tu WhatsApp. Con esto, registraras tu número
para usarlo en el entorno de pruebas.

## Development

Ir a la URL: `https://console.twilio.com/us1/develop/sms/settings/whatsapp-sandbox?frameUrl=%2Fconsole%2Fsms%2Fwhatsapp%2Fsandbox%3Fx-target-region%3Dus1`
cambiar el webhook que se llamará cuando se reciba un mensaje.
Si se usa esta app, poner algo así: `https://mi_sitio/reply`, puede usar [ngrok](https://ngrok.com/download)
para hacerla accesible en internet.
