# Archivos de Sonido para el Casino

Esta carpeta debe contener los siguientes archivos de sonido (.mp3 o .ogg):

## Sonidos de Slots

1. **slot_spin.mp3** - Sonido de giro de rodillos (loop de 2-3 segundos)
   - Ejemplo: sonido mecánico de tragamonedas girando

2. **reel_stop.mp3** - Sonido cuando un rodillo se detiene
   - Ejemplo: "clack" mecánico corto

3. **win_normal.mp3** - Sonido de victoria normal
   - Ejemplo: campanas, fanfarria corta (1-2 segundos)

4. **win_big.mp3** - Sonido de victoria grande
   - Ejemplo: fanfarria más elaborada, trompetas (2-3 segundos)

5. **jackpot.mp3** - Sonido de jackpot
   - Ejemplo: celebración épica, sirena, monedas cayendo (4-5 segundos)

6. **lose.mp3** - Sonido de derrota
   - Ejemplo: sonido bajo, triste, corto (0.5 segundos)

7. **coin_drop.mp3** - Sonido de monedas cayendo
   - Ejemplo: sonido metálico de monedas (0.5 segundos)

8. **ui_click.mp3** - Sonido de click en botones
   - Ejemplo: click mecánico corto

## Fuentes de Sonidos Gratuitos

Puedes descargar sonidos gratuitos de:
- https://freesound.org/ (buscar "slot machine", "casino", "coins")
- https://mixkit.co/free-sound-effects/
- https://www.zapsplat.com/

## Instrucciones

1. Descarga los sonidos en formato .mp3
2. Renómbralos exactamente como se indica arriba
3. Colócalos en esta carpeta: `app/src/main/res/raw/`
4. Rebuild el proyecto

**Nota:** Si no tienes sonidos, el juego funcionará igual pero sin audio.
