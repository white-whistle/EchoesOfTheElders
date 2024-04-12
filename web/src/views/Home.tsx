import { Link } from 'wouter';
import { MCGui } from '../components/MCGui';
import { MCText } from '../components/MCText';
import { Anchor } from '@mantine/core';
import { MC_PALETTE } from '../logic/MCPalette';
import { Horizontal } from '../Layout';

export const Home = () => {
	return (
		<Horizontal justify='center'>
			<MCGui>
				<MCText c={MC_PALETTE.dark_gray} shadowColor={MC_PALETTE.gray}>
					Welcome to EOTE's web interface!
				</MCText>
				<br />
				<Anchor component={Link} to='/items'>
					<MCText>Items</MCText>
				</Anchor>
			</MCGui>
		</Horizontal>
	);
};
