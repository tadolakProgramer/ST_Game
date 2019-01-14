package com.todal.game.Screenns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;




    public class Background {
        private TiledMap map;
        private OrthogonalTiledMapRenderer renderer;
        private float scale;

        public Background() {

            scale = Gdx.graphics.getWidth()/800f;
            map = new TmxMapLoader().load("data/mapa.tmx");
            renderer = new OrthogonalTiledMapRenderer(map,scale);

        }

        public void update(OrthographicCamera camera) {

        }

        public void render(OrthographicCamera camera) {
            renderer.setView(camera);
            renderer.render();
        }

        public void dispose() {
            map.dispose();
            renderer.dispose();
        }

        public TiledMap getMap() {
            return map;
        }

        public void setMap(TiledMap map) {
            this.map = map;
        }

        public OrthogonalTiledMapRenderer getRenderer() {
            return renderer;
        }

        public void setRenderer(OrthogonalTiledMapRenderer renderer) {
            this.renderer = renderer;
        }

    }

