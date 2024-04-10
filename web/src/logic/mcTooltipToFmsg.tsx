import { FormattedMessage } from 'react-intl';
import { Ability, Arg, EOTEArgs, ItemMeta, TextMessage } from '../types';
import { Vertical } from '../Layout';
import { MCText } from '../MCText';

export function argToValue(arg: Arg) {
	switch (typeof arg) {
		case 'string':
		case 'number':
			return arg;
	}

	return textMessageToFmsg(arg);
}

export function eoteArgsToValues(args: EOTEArgs) {
	if (!args) return {};

	const values = Object.fromEntries(
		Object.entries(args).map(([k, v]) => [k, argToValue(v)])
	);
	return values;
}

export function textMessageToFmsg(msg: TextMessage) {
	if (TextMessage.isMC(msg)) {
		if (msg.translate === 'number.echoes_of_the_elders.int')
			return msg.with[0];
		if (msg.translate === 'number.echoes_of_the_elders.f1')
			return Number(msg.with[0]).toFixed(1);
		// technically we cant support this really and should shift to using eote messages instead where possible
		return <FormattedMessage id={msg.translate} values={{}} />;
	}

	if (TextMessage.isEOTE(msg)) {
		const values = eoteArgsToValues(msg.with);
		return (
			<FormattedMessage
				id={msg['echoes_of_the_elders:translate']}
				values={values}
			/>
		);
	}

	return null;
}

const AbilityTooltip = ({ ability }: { ability: Ability }) => {
	return (
		<Vertical>
			<MCText>
				<FormattedMessage id={ability.name + '.name'} />
			</MCText>
			{ability.info.map((info) => {
				return (
					<MCText key={info.name}>
						<FormattedMessage
							id={ability.name + '.' + info.name}
							values={eoteArgsToValues(info.args)}
						/>
					</MCText>
				);
			})}
		</Vertical>
	);
};

export const ArtifactAbilities = ({ item }: { item: ItemMeta }) => {
	if (!item.isArtifact) return null;

	const { abilities = [] } = item;

	return (
		<>
			{abilities.map((ability) => {
				return <AbilityTooltip ability={ability} key={ability.name} />;
			})}
		</>
	);
};
