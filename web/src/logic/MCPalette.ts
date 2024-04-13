export const MC_PALETTE = {
	white: '#FFFFFF',
	yellow: '#FFFF55',
	light_purple: '#FF55FF',
	red: '#FF5555',
	aqua: '#55FFFF',
	green: '#55FF55',
	blue: '#5555FF',
	dark_gray: '#555555',
	gray: '#AAAAAA',
	gold: '#FFAA00',
	dark_purple: '#AA00AA',
	dark_red: '#AA0000',
	dark_aqua: '#00AAAA',
	dark_green: '#00AA00',
	dark_blue: '#0000AA',
	black: '#000000',
} as const;

export function tryMcGetColor(color: string) {
	return MC_PALETTE[color] ?? color;
}

const { black, ...MC_BRIGHT_PALETTE } = MC_PALETTE;

export { MC_BRIGHT_PALETTE };