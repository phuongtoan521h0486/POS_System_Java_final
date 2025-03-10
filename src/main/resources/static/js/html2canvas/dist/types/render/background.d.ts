import {Bounds} from '../css/layout/bounds';
import {BACKGROUND_ORIGIN} from '../css/property-descriptors/background-origin';
import {ElementContainer} from '../dom/element-container';
import {BackgroundSizeInfo} from '../css/property-descriptors/background-size';
import {Vector} from './vector';
import {BACKGROUND_REPEAT} from '../css/property-descriptors/background-repeat';
import {CSSValue} from '../css/syntax/parser';
import {Path} from './path';
import {BACKGROUND_CLIP} from '../css/property-descriptors/background-clip';

export declare const calculateBackgroundPositioningArea: (backgroundOrigin: BACKGROUND_ORIGIN, element: ElementContainer) => Bounds;
export declare const calculateBackgroundPaintingArea: (backgroundClip: BACKGROUND_CLIP, element: ElementContainer) => Bounds;
export declare const calculateBackgroundRendering: (container: ElementContainer, index: number, intrinsicSize: [number | null, number | null, number | null]) => [Path[], number, number, number, number];
export declare const isAuto: (token: CSSValue) => boolean;
export declare const calculateBackgroundSize: (size: BackgroundSizeInfo[], [intrinsicWidth, intrinsicHeight, intrinsicProportion]: [number | null, number | null, number | null], bounds: Bounds) => [number, number];
export declare const getBackgroundValueForIndex: <T>(values: T[], index: number) => T;
export declare const calculateBackgroundRepeatPath: (repeat: BACKGROUND_REPEAT, [x, y]: [number, number], [width, height]: [number, number], backgroundPositioningArea: Bounds, backgroundPaintingArea: Bounds) => [Vector, Vector, Vector, Vector];
