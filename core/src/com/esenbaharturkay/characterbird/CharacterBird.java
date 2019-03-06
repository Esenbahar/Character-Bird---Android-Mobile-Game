package com.esenbaharturkay.characterbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class CharacterBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture beep;
	Texture beep3;
 	Texture beep2;


	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

	float birdX = 0;
	float birdY = 0;
    int gameState = 0;
    float velocity = 0;
    float gravity = 0.3f;
	Random random;

	float velocityBackground = 0;

	int numberOfEnemies = 4;
	float []  enemyX = new float[numberOfEnemies];
	float distance = 0;
	float enemyVelocity = 3;
	float [] enemyOffSet = new float[numberOfEnemies];
	float [] EnemyOffSet3 = new float[numberOfEnemies];
	float [] EnemyOffSet2 = new float[numberOfEnemies];

	Circle birdCircle;

	Circle[] enemyCircles;
	Circle[] enemyCircles3;
	Circle[] enemyCircles2;



	String [] dizi = new String[8];


	boolean atouched;

	ShapeRenderer shapeRenderer;

	@Override
	public void create () {
 	//Oyun başladığında neler olacaksa buraya yazarız.

		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
  		beep = new Texture("strawberry.png");
		beep2 = new Texture("strawberry.png");
		beep3 = new Texture("strawberry.png");



		distance = Gdx.graphics.getWidth()/8; //iki set arasında ekranın yarısı kadar fark olsun dedim.
		random = new Random();
		atouched = false;

		birdX = Gdx.graphics.getWidth()/2 - bird.getHeight();
		birdY = Gdx.graphics.getHeight()/2 ;

		birdCircle  = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);



		shapeRenderer = new ShapeRenderer();

		for (int i = 0; i < numberOfEnemies; i++){

			enemyOffSet[i] = (random.nextFloat() - 0.5f ) * (Gdx.graphics.getHeight() - 200);
			EnemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()- 200);
			EnemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()- 200);


			enemyX[i] =  Gdx.graphics.getWidth() - beep.getWidth()/2 + i * distance;

			enemyCircles[i] = new Circle();
			enemyCircles3 [i] = new Circle();
			enemyCircles2 [i] = new Circle();


		}


	}

	@Override
	public void render () {

		//Hareketleri burada yazarız.Çizme işlemlerini buraya yazarız.

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		if (gameState == 1) { //Oyun başladıysa olacaklar.

			if (enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2) {
				score++;

				if (scoredEnemy < numberOfEnemies - 1) {
					scoredEnemy++;
				} else {
					scoredEnemy = 0;
				}
			}
				if (Gdx.input.justTouched()) {//Kullanıcı tıkladığında olacaklar.


					velocity = -10;
				}

				for (int i = 0; i < numberOfEnemies; i++) {

					if (enemyX[i] < Gdx.graphics.getWidth() / 10) {
						enemyX[i] = enemyX[i] + numberOfEnemies * distance;


						enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						EnemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						EnemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);


					} else {
						enemyX[i] = enemyX[i] - enemyVelocity;
					}


					batch.draw(beep, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet[i], Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
					batch.draw(beep3, enemyX[i], Gdx.graphics.getHeight() / 2 + EnemyOffSet3[i], Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
					batch.draw(beep2, enemyX[i], Gdx.graphics.getHeight() / 2 + EnemyOffSet2[i], Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

					enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 40);
					enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + EnemyOffSet3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 40);
					enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + EnemyOffSet2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 40);


				}

				if (birdY > 0) {

					velocity = velocity + gravity;
					birdY = birdY - velocity;

				} else {
					gameState = 2;
				}
			} else if (gameState == 0) {
				//oyun başlamadıysa başlat.
				if (Gdx.input.justTouched()) {//Kullanıcı tıkladığında olacaklar.

					gameState = 1; //Oyun başladı.
				}
			} else if (gameState == 2) {
				//oyunbittiğinde adam tekrar tıkladıyda oyunu tekrar başlat.

			font2.draw(batch,"Game Over! Tap To Play Again!",100,Gdx.graphics.getHeight() / 2);

			if (Gdx.input.justTouched()) {//Kullanıcı tıkladığında olacaklar.

					gameState = 1; //Oyun başladı.

					birdY = Gdx.graphics.getHeight() / 3;

					for (int i = 0; i < numberOfEnemies; i++) {

						enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						EnemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						EnemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);


						enemyX[i] = Gdx.graphics.getWidth() - beep.getWidth() / 2 + i * distance;

						enemyCircles[i] = new Circle();
						enemyCircles3[i] = new Circle();
						enemyCircles2[i] = new Circle();


					}
					velocity = 0;
					scoredEnemy = 0;
					score = 0;
				}
			}

			batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

		   font.draw(batch,String.valueOf(score),100,200);

			batch.end();

			birdCircle.set(birdX + Gdx.graphics.getWidth() / 30, birdY + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);

			// shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			//shapeRenderer.setColor(Color.BLACK);
			//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);


			for (int i = 0; i < numberOfEnemies; i++) {
				//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/20 , Gdx.graphics.getHeight()/2 + enemyOffSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/40 );
				//shapeRenderer.circle(enemyX[i]+350 + Gdx.graphics.getWidth()/20 , Gdx.graphics.getHeight()/2 + EnemyOffSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/40 );
				//shapeRenderer.circle(enemyX[i] -480+ Gdx.graphics.getWidth()/20 , Gdx.graphics.getHeight()/2 + EnemyOffSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/40 );


				if (Intersector.overlaps(birdCircle, enemyCircles[i])|| Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i])) {

					gameState = 2;

				}

			}
			shapeRenderer.end();
		}

		@Override
		public void dispose () {

		}
	}
