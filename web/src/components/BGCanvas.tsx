import { useEffect, useRef } from 'react';
import GlslCanvas from 'glslCanvas';
import endPortalShader from '../assets/shader/endportal.glsl?raw';
import endPortalSkyTexture from '../assets/texture/end_sky.png';
import endPortalTexture from '../assets/texture/end_portal.png';
import {
	Box,
	BoxComponentProps,
	PolymorphicComponentProps,
} from '@mantine/core';

export const BGCanvas = ({
	children,
	...rest
}: PolymorphicComponentProps<'div', BoxComponentProps>) => {
	const ref = useRef<HTMLCanvasElement>(null);

	useEffect(() => {
		const canvas = ref.current;
		const sandbox = new GlslCanvas(canvas);

		sandbox.load(endPortalShader);

		sandbox.setUniform(
			'COLORS',
			[0.022087, 0.098399, 0.110818],
			[0.011892, 0.095924, 0.089485],
			[0.027636, 0.101689, 0.100326],
			[0.046564, 0.109883, 0.114838],
			[0.064901, 0.117696, 0.097189],
			[0.063761, 0.086895, 0.123646],
			[0.084817, 0.111994, 0.16638],
			[0.097489, 0.15412, 0.091064],
			[0.106152, 0.131144, 0.195191],
			[0.097721, 0.110188, 0.187229],
			[0.133516, 0.138278, 0.148582],
			[0.070006, 0.243332, 0.235792],
			[0.196766, 0.142899, 0.214696],
			[0.047281, 0.315338, 0.32197],
			[0.204675, 0.39001, 0.302066],
			[0.080955, 0.314821, 0.66149]
		);

		sandbox.setUniform('Sampler0', endPortalSkyTexture);
		sandbox.setUniform('Sampler1', endPortalTexture);

		canvas?.setAttribute('width', sandbox.width);
		canvas?.setAttribute('height', sandbox.height);

		return () => {
			sandbox.unsubscribeAll();
		};
	}, []);

	return (
		<Box pos='relative' {...rest}>
			<canvas
				ref={ref}
				style={{
					width: '100vw',
					height: '100vh',
					position: 'fixed',
					top: 0,
					left: 0,
					right: 0,
					bottom: 0,
					zIndex: 0,
				}}
			/>
			<Box
				w='100%'
				h='100%'
				pos='absolute'
				left={0}
				top={0}
				style={{ zIndex: 1 }}
			>
				{children}
			</Box>
		</Box>
	);
};
