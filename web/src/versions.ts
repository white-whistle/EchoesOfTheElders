import TitleSpiritualAwakening from '../../design/title_spiritual_awakening.png';
import TitleExtraction from '../../design/title_extraction.png';


export const VERSIONS = [
	{
		name: 'spiritual awakening',
		image: TitleSpiritualAwakening,
		version: "1.2",
		changes: [
			"+ the spirit realm dimension",
			"+ cache structures",
			"+ many artifacts",
		],
	},
	{
		name: 'extraction',
		image: TitleExtraction,
		version: "2",
		changes: [
			"+ raids",
			"+ artifact upgrades",
			"+ many artifacts",
			"+ overall polish",
		]
	},

]

export const CURRENT_VERSION = VERSIONS[VERSIONS.length - 1];