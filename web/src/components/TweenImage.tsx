import { ImageProps, PolymorphicComponentProps } from '@mantine/core';
import BorderImage from './BorderImage';
import styles from './TweenImage.module.css';

const TweenImage = ({
	...rest
}: PolymorphicComponentProps<'img', ImageProps>) => {
	return <BorderImage className={styles.image} {...rest} />;
};

export default TweenImage;
