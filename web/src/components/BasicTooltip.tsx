import { FormattedMessage } from 'react-intl';
import { Vertical } from '../Layout';
import { MCText } from './MCText';
import { textMessageIdentifier, textMessageToFmsg } from '../logic/TextMessage';
import { itemToKey } from './Item';
import { ItemMeta } from '../types';

export const BasicTooltip = ({ item }: { item: ItemMeta }) => {
	return (
		<Vertical gap='0'>
			<MCText>
				<FormattedMessage id={itemToKey(item.item)} />
			</MCText>
			{/* 
						<ArtifactAbilities item={item} />

						{Boolean(item.dropData) && (
							<Vertical gap='0'>
								<MCText>Drops from raids:</MCText>
								<MCText>
									Level {item.dropData!.min}~
									{item.dropData!.max}
								</MCText>
							</Vertical>
						)} */}
			{(item.tooltip ?? []).map((text, index) => (
				<MCText key={textMessageIdentifier(text) + '_' + index}>
					{textMessageToFmsg(text)}
				</MCText>
			))}
		</Vertical>
	);
};
