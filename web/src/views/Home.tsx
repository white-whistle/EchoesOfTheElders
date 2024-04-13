import { Link } from 'wouter';
import { MCGui } from '../components/MCGui';
import { MCText } from '../components/MCText';
import { Anchor, Grid } from '@mantine/core';
import { MC_PALETTE } from '../logic/MCPalette';
import { Horizontal, Vertical } from '../Layout';
import { PixelScaling } from '../components/PixelScaling';

export const Home = () => {
	const { px } = PixelScaling.use();
	return (
		<Horizontal justify='center'>
			<MCGui>
				<Vertical gap={px(10)} p={px(4)}>
					<MCText
						c={MC_PALETTE.dark_gray}
						shadowColor={MC_PALETTE.gray}
					>
						Welcome to EOTE's web interface!
					</MCText>

					<Grid gutter={px(4)} columns={16}>
						<Grid.Col span={4}>
							<MCGui>getting started</MCGui>
						</Grid.Col>

						<Grid.Col span={4}>
							<MCGui>items</MCGui>
						</Grid.Col>

						<Grid.Col span={4}>
							<MCGui>philosophy</MCGui>
						</Grid.Col>

						<Grid.Col span={4}>
							<MCGui>technology</MCGui>
						</Grid.Col>
					</Grid>

					<Anchor component={Link} to='/items'>
						<MCText>Items</MCText>
					</Anchor>
				</Vertical>
			</MCGui>
		</Horizontal>
	);
};
