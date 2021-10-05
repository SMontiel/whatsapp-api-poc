package sm;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;
import spark.utils.StringUtils;

/**
 * Friendly name: WhatsApp-API-PoC
 * Key Type:      Standard
 * SID:           SK4538511defac33801121f8b61c847f1d
 * Secret:        3gWcvKBv6esRX1rpUCGwwmK6ehHZ3PTR
 *
 *  * Friendly name: WhatsApp-API-PoC-Main
 *  * Key Type:      Main
 *  * SID:           SK8f535ed6fe1db263a65715978bf80f28
 *  * Secret:        izxYTVRoxhH2ltqGlhs61ed8ihxLBDCV
 *
 * PRODUCTION
 * Account SID: AC2d7603b34f7c82823fd7a4d39166c6c6
 * Auth token:  58ceb78ae41253f3dcba73f11101f1ca
 *
 * TEST
 * Account SID: AC9950dffafc23cb5fee533e53b3d5f398
 * Auth token:  76eabc4fc1d88571de30bf8823b2321f
 *
 * Default reply URL: https://timberwolf-mastiff-9776.twil.io/demo-reply
 */
public class Main {
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	private static final String ACCOUNT_SID = "AC2d7603b34f7c82823fd7a4d39166c6c6";
	private static final String AUTH_TOKEN = "58ceb78ae41253f3dcba73f11101f1ca";
	private static final String FROM_NUMBER = "whatsapp:+14155238886";
	private static final String YOUR_NUMBER = "7891194633"; // Put your number here
	private static final String DEFAULT_TO_NUMBER = "whatsapp:+521" + YOUR_NUMBER;

	public static void main(String[] args) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		Spark.port(8090);

		Spark.get("/hello", (req, res) -> "Hello World");

		/**
		 * De acuerdo a WhatsApp, la primera vez se debe enviar un template message a cualquier usuario. Debe ser
		 * Template message, estos se mandan a WhatsApp para que lo aprueben y se pueda usar.
		 *
		 * Aquí se usa el template message 'Your {{1}} code is {{2}}'. Este es facilitado por Twilio para pruebas.
		 */
		Spark.get("/send-template-message", (req, res) -> {
			log.info("URL: " + req.url());
			String toNumber = DEFAULT_TO_NUMBER;
			String toParam = req.queryParams("TO_NUMBER");
			if (StringUtils.hasLength(toParam)) {
				toNumber = "whatsapp:+521" + toParam;
			}
			log.info("To number: " + toNumber);
			Message message = Message.creator(
							new com.twilio.type.PhoneNumber(toNumber),
							new com.twilio.type.PhoneNumber(FROM_NUMBER),
							"Your Cazvi code is 12345")
					.create();
			log.info("Message: " + message);

			return message;
		});

		Spark.get("/send-hello", (req, res) -> {
			log.info("URL: " + req.url());
			String toNumber = DEFAULT_TO_NUMBER;
			String toParam = req.queryParams("TO_NUMBER");
			if (StringUtils.hasLength(toParam)) {
				toNumber = "whatsapp:+521" + toParam;
			}
			log.info("To number: " + toNumber);
			Message message = Message.creator(
							new com.twilio.type.PhoneNumber(toNumber),
							new com.twilio.type.PhoneNumber(FROM_NUMBER),
							"Hello there!")
					.create();
			log.info("Message: " + message);

			return message;
		});

		Spark.get("/send-hello-with-image", (req, res) -> {
			log.info("URL: " + req.url());
			String toNumber = DEFAULT_TO_NUMBER;
			String toParam = req.queryParams("TO_NUMBER");
			if (StringUtils.hasLength(toParam)) {
				toNumber = "whatsapp:+521" + toParam;
			}
			log.info("To number: " + toNumber);
			// Se puede enviar en un solo mensaje:
			// - Un string
			// - Una imágen
			// - Un string y una imágen
			// > NO se pueden enviar más de una imagen por mensaje
			Message message = Message.creator(
							new com.twilio.type.PhoneNumber(toNumber),
							new com.twilio.type.PhoneNumber(FROM_NUMBER),
							"Hello there!")
					.setMediaUrl("https://farm8.staticflickr.com/7090/6941316406_80b4d6d50e_z_d.jpg")
					.create();
			log.info("Message: " + message);

			return message;
		});

		Spark.get("/send-images", (req, res) -> {
			log.info("URL: " + req.url());
			String toNumber = DEFAULT_TO_NUMBER;
			String toParam = req.queryParams("TO_NUMBER");
			if (StringUtils.hasLength(toParam)) {
				toNumber = "whatsapp:+521" + toParam;
			}
			log.info("To number: " + toNumber);
			Message message = Message.creator(
							new com.twilio.type.PhoneNumber(toNumber),
							new com.twilio.type.PhoneNumber(FROM_NUMBER),
							"Image 1")
					.setMediaUrl("https://farm8.staticflickr.com/7090/6941316406_80b4d6d50e_z_d.jpg")
					.create();
			log.info("Message 1: " + message);

			message = Message.creator(
							new com.twilio.type.PhoneNumber(toNumber),
							new com.twilio.type.PhoneNumber(FROM_NUMBER),
							"Image 2")
					.setMediaUrl("https://www.welivesecurity.com/wp-content/uploads/es-la/2012/12/Logo-Android.png")
					.create();
			log.info("Message 2: " + message);

			return message;
		});

		/**
		 * Este endpoint se ejecuta cada vez que el usuario contesta un mensaje
		 */
		Spark.post("/reply", (req, res) -> {
			log.info("URL: " + req.url());
//			log.info("Query params: " + req.queryParams());
//			log.info("Params: " + req.params());
//			log.info("Cookies: " + req.cookies());
//			log.info("Attrs: " + req.attributes());
//			log.info("Headers: " + req.headers());
//			log.info("---------------------------------------------------");
			log.info("Body: " + req.queryParams("Body"));
			log.info("From: " + req.queryParams("From"));
			log.info("To: " + req.queryParams("To"));
			log.info("ApiVersion: " + req.queryParams("ApiVersion"));
			log.info("SmsSid: " + req.queryParams("SmsSid"));
			log.info("SmsStatus: " + req.queryParams("SmsStatus"));
			log.info("SmsMessageSid: " + req.queryParams("SmsMessageSid"));
			log.info("ProfileName: " + req.queryParams("ProfileName"));
			log.info("NumSegments: " + req.queryParams("NumSegments"));
			log.info("WaId: " + req.queryParams("WaId"));
			log.info("MessageSid: " + req.queryParams("MessageSid"));
			log.info("AccountSid: " + req.queryParams("AccountSid"));
			int numMedia = Integer.parseInt(req.queryParams("NumMedia"));
			log.info("NumMedia: " + numMedia);
			if (numMedia > 0) {
				for (int i = 0; i < numMedia; i++) {
					log.info("MediaUrl" + i + ": " + req.queryParams("MediaUrl" + i));
					log.info("MediaContentType" + i + ": " + req.queryParams("MediaContentType" + i));
				}
			}

			res.type("application/xml");
			Body body = new Body
					.Builder("The Robots are coming! Head for the hills!")
					.build();
			com.twilio.twiml.messaging.Message sms = new com.twilio.twiml.messaging.Message
					.Builder()
					.body(body) // Solo puede enviar una imágen
					.media(new Media.Builder("https://farm8.staticflickr.com/7090/6941316406_80b4d6d50e_z_d.jpg").build())
					.build();
			MessagingResponse twiml = new MessagingResponse
					.Builder()
					.message(sms)
					.build();
			return twiml.toXml();
		});
	}

	/**
	 * Response:
	 * Message(
	 * 		body=Hello there!,
	 * 		numSegments=1,
	 * 		direction=outbound-api,
	 * 		from=whatsapp:+14155238886,
	 * 		to=whatsapp:+5217891194633,
	 * 		dateUpdated=2021-10-05T17:36:52Z,
	 * 		price=null,
	 * 		errorMessage=null,
	 * 		uri=/2010-04-01/Accounts/AC2d7603b34f7c82823fd7a4d39166c6c6/Messages/SM0fdbb4ef2fe34db3ae9f4b46ff9dbecf.json,
	 * 		accountSid=AC2d7603b34f7c82823fd7a4d39166c6c6,
	 * 		numMedia=0,
	 * 		status=queued,
	 * 		messagingServiceSid=null,
	 * 		sid=SM0fdbb4ef2fe34db3ae9f4b46ff9dbecf,
	 * 		dateSent=null,
	 * 		dateCreated=2021-10-05T17:36:52Z,
	 * 		errorCode=null,
	 * 		priceUnit=null,
	 * 		apiVersion=2010-04-01,
	 * 		subresourceUris={
	 * 			media=/2010-04-01/Accounts/AC2d7603b34f7c82823fd7a4d39166c6c6/Messages/SM0fdbb4ef2fe34db3ae9f4b46ff9dbecf/Media.json
	 * 		}
	 * )
	 */
	// SID: SMcf7e8d81692c4a31b7793e8de4ff6b8c
	// SM4539afe6b6614311a33365e639c5704f
}
