export type Arg = number | string | TextMessage
export type MCArgs = Arg[];
export type EOTEArgs = { [key: string]: Arg };

export type TextMessageBase = {
	font?: string,
	color?: string,
}

export type MCTextMessage = TextMessageBase & {
	translate: string,
	with: MCArgs;
}

export type EOTETextMessage = TextMessageBase & {
	"echoes_of_the_elders:translate": string,
	with: EOTEArgs;
}

export type PlainTextMessage = TextMessageBase & {
	text: string,
}

export const TextMessage = {
	isMC(msg: TextMessage): msg is MCTextMessage {
		return "translate" in msg
	},

	isEOTE(msg: TextMessage): msg is EOTETextMessage {
		return 'echoes_of_the_elders:translate' in msg
	},

	isPlain(msg: TextMessage): msg is PlainTextMessage {
		return 'text' in msg
	},
}

export type TextMessage = MCTextMessage | EOTETextMessage | PlainTextMessage;

export type Ability = {
	name: string;
}


export type ItemMeta = {
	item: string;
	name: string;

	dropData?: {
		min: number;
		max: number;
	};

	rarity?: string;
	isReward?: boolean;

	isArtifact?: boolean;
	abilities?: Ability[];

	tooltip: TextMessage[];
};
