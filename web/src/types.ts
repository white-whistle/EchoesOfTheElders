export type Arg = number | string | TextMessage
export type MCArgs = Arg[];
export type EOTEArgs = { [key: string]: Arg };

export type MCTextMessage = {
	translate: string,
	with: MCArgs;
}

export type EOTETextMessage = {
	"echoes_of_the_elders:translate": string,
	with: EOTEArgs;
}

export const TextMessage = {
	isMC(msg: TextMessage): msg is MCTextMessage {
		return "translate" in msg
	},

	isEOTE(msg: TextMessage): msg is EOTETextMessage {
		return 'echoes_of_the_elders:translate' in msg
	},
}

export type TextMessage = MCTextMessage | EOTETextMessage;


export type AbilityInfo = {
	name: string;
	args: EOTEArgs;
}

export type Ability = {
	name: string;
	info: AbilityInfo[];
}


export type ItemMeta = {
	item: string;

	dropData?: {
		min: number;
		max: number;
	};

	rarity?: string;
	isReward?: boolean;

	isArtifact?: boolean;
	abilities?: Ability[];
};
