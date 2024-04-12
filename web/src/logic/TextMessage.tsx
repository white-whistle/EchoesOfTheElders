import { FormattedMessage } from 'react-intl';
import { Arg, EOTEArgs, TextMessage } from '../types';
import { tryMcGetColor } from './MCPalette';
import { MCGlyphIcon } from '../components/MCGlyphIcon';

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
	const getTextContent = () => {
		if (TextMessage.isPlain(msg)) {
			if (msg.font) {
				return <MCGlyphIcon font={msg.font} />;
			}

			return msg.text;
		}

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
	};

	const content = getTextContent();

	if (!content) return null;
	const style = { color: tryMcGetColor(msg.color) };

	return <span style={style}>{content}</span>;
}

export function textMessageIdentifier(msg: TextMessage) {
	if (TextMessage.isMC(msg)) {
		return msg.translate;
	}

	if (TextMessage.isEOTE(msg)) {
		return msg['echoes_of_the_elders:translate'];
	}

	return null;
}
